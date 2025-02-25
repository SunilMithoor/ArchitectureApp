package com.sunil.app.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHandler @Inject constructor(@ApplicationContext private val context: Context) {

    /**
     * Checks if there is an active network connection (Wi-Fi or Cellular).
     *
     * This method uses the ConnectivityManager to determine if the device has an active network
     * connection. It checks for both Wi-Fi and Cellular connections.
     *
     * @return `true` if there is an active network connection, `false` otherwise.
     */
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager// For devices running Android M (API 23) and above, use NetworkCapabilities
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

    /**
     * Checks if the device is online (connected to the internet).
     *
     * This method is similar to [isNetworkAvailable] but is renamed to better reflect its purpose.
     * It checks for both Wi-Fi and Cellular connections.
     *
     * @return `true` if the device is online, `false` otherwise.
     */
    fun isOnline(): Boolean = isNetworkAvailable()

}
