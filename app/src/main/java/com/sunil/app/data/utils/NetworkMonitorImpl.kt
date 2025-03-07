package com.sunil.app.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import com.sunil.app.domain.utils.NetworkMonitor
import com.sunil.app.domain.utils.NetworkState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * Implementation of [NetworkMonitor] that uses the ConnectivityManager to monitor network changes.
 *
 * This class provides a flow of [NetworkState] that emits updates whenever the network
 * connectivity changes. It also checks for internet access and network validation.
 *
 * @param appContext The application context.
 */
class NetworkMonitorImpl(
    appContext: Context
) : NetworkMonitor {

    private val connectivityManager =
        appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    /**
     * Indicates whether the device is currently offline.
     *
     * This variable is used to determine if a network state change from offline to online
     * should trigger a refresh.
     */
    private var isCurrentlyOffline: Boolean = !isNetworkAvailable()

    /**
     * A flow of [NetworkState] that emits updates whenever the network connectivity changes.
     *
     * This flow emits a new [NetworkState] whenever the network becomes available or lost.
     * It also checks for internet access and network validation.
     */
    override val networkState: Flow<NetworkState> = callbackFlow {
        // Send the initial network state
        send(createNetworkState(isNetworkAvailable()))

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Timber.d("Network is available")
                val networkState = createNetworkState(true)
                launch { send(networkState) }
                isCurrentlyOffline = false
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                Timber.d("Network is lost")
                val networkState = createNetworkState(false)
                launch { send(networkState) }
                isCurrentlyOffline = true
            }

            /**
             * Called when the network capabilities change.
             *
             * This method is called when the capabilities of a network change, such as when
             * it gains or loses internet access.
             *
             * @param network The network whose capabilities have changed.
             * @param networkCapabilities The new capabilities of the network.
             */
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
//                val isValidated =
//                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

                val isValidated=   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Use NET_CAPABILITY_VALIDATED only on API 23+
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                } else {
                    // On older devices, only check for NET_CAPABILITY_INTERNET
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                }
                Timber.d("Network capabilities changed. isValidated: $isValidated")
                // You might want to send a new state here if needed
                // launch { send(createNetworkState(isValidated)) }

            }
        }

        // Use a NetworkRequest to specify the type of network you're interested in
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        // Register the callback with the NetworkRequest
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }.distinctUntilChanged()

    /**
     * Creates a [NetworkState] object based on the current network status.
     *
     * @param isOnline True if the network is online, false otherwise.
     * @return A [NetworkState] object representing the current network state.
     */
    private fun createNetworkState(isOnline: Boolean): NetworkState {
        // Use the more descriptive variable name
        val shouldRefresh = isCurrentlyOffline && isOnline
        return NetworkState(isOnline = isOnline, shouldRefresh = shouldRefresh)
    }

    /**
     * Checks if the device has a network connection with internet access.
     *
     * @return True if the device has a network connection with internet access, false otherwise.
     */
    private fun isNetworkAvailable(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(network) ?: return false

            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
        } else {
            // For devices running below Android M, use NetworkInfo (deprecated but necessary)
            @Suppress("DEPRECATION")
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            return networkInfo?.isConnectedOrConnecting ?: false
        }
    }
}
