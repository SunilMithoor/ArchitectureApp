package com.sunil.app.data.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import com.sunil.app.BuildConfig
import com.sunil.app.R
import timber.log.Timber
import java.util.Locale


/**
 * Utility class for building a custom User-Agent string.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
object UserAgentBuilder {

    private const val TAG = "UserAgentBuilder"

    /**
     * Builds a custom User-Agent string for network requests.
     *
     * @param context The application context.
     * @return The generated User-Agent string.
     */
    fun buildUserAgent(context: Context): String {
        val packageInfo = getPackageInfo(context)
        val appName = getAppName(context)
        val versionName = packageInfo?.versionName ?: "Unknown"
        val versionCode = getVersionCode(packageInfo)
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        val sdkVersion = VERSION.SDK_INT
        val androidVersion = VERSION.RELEASE
        val installerName = getInstallerPackageName(context)
        val screenResolution = getScreenResolution(context)
        val locale = Locale.getDefault()
        val language = locale.language
        val country = locale.country
        val installSource = getInstallSource(context)
        val buildType = if (BuildConfig.DEBUG) "debug" else "release"
        val networkType = getNetworkType(context)

        return "$appName/$versionName ($versionCode) ($manufacturer; $model; SDK $sdkVersion; Android $androidVersion; $installerName); Screen $screenResolution; Locale $language-$country; InstallSource $installSource; BuildType $buildType; Network $networkType"
    }

    /**
     * Retrieves the application's package information.
     *
     * @param context The application context.
     * @return The PackageInfo object, or null if an error occurs.
     */
    private fun getPackageInfo(context: Context): PackageInfo? {
        return try {
            if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
                context.packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.PackageInfoFlags.of(0L)
                )
            } else {
                context.packageManager.getPackageInfo(context.packageName, 0)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Timber.tag(TAG).e(e, "Error getting package info")
            null
        }
    }

    /**
     * Retrieves the application's name from resources.
     *
     * @param context The application context.
     * @return The application name, or "Unknown" if not found.
     */
    private fun getAppName(context: Context): String {
        return try {
            context.getString(R.string.app_name)
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Error getting app name")
            "Unknown"
        }
    }

    /**
     * Retrieves the application's version code.
     *
     * @param packageInfo The PackageInfo object.
     * @return The version code, or -1 if not found.
     */
    private fun getVersionCode(packageInfo: PackageInfo?): Long {
        return packageInfo?.let {
            if (VERSION.SDK_INT >= VERSION_CODES.P) {
                it.longVersionCode
            } else {
                @Suppress("DEPRECATION")
                it.versionCode.toLong()
            }
        } ?: -1
    }


    /*** Retrieves the installer package name for the application using the modern
     * getInstallSourceInfo() method, handling deprecation gracefully.
     *
     * @param context The application context.
     * @return The installer package name, or a default value if not found.
     */
    private fun getInstallerPackageName(context: Context): String {
        val packageManager = context.packageManager
        val packageName = context.packageName

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Use getInstallSourceInfo() for Android R (API 30) and above
            try {
                val installSourceInfo = packageManager.getInstallSourceInfo(packageName)
                val installingPackageName = installSourceInfo.installingPackageName
                when {
                    installingPackageName.isNullOrBlank() -> "Unknown"
                    else -> installingPackageName
                }
            } catch (e: PackageManager.NameNotFoundException) {
                // Handle the case where the package is not found (shouldn't happen for the current app)
                "Unknown"
            }
        } else {
            // Use getInstallerPackageName() for older Android versions (deprecated but necessary)
            @Suppress("DEPRECATION")
            val installerPackageName = packageManager.getInstallerPackageName(packageName)
            when {
                installerPackageName.isNullOrBlank() -> "Unknown"
                else -> installerPackageName
            }
        }
    }

    /**
     * Retrieves the screen resolution.
     *
     * @param context The application context.
     * @return The screen resolution as a string,or "Unknown" if not found.
     */
    private fun getScreenResolution(context: Context): String {
        return try {
            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            "${displayMetrics.widthPixels}x${displayMetrics.heightPixels}"
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Error getting screen resolution")
            "Unknown"
        }
    }

    /**
     * Determines the current network type (WiFi, Mobile, or Unknown).
     *
     * @param context The application context.
     * @return A string representing the network type.
     */
    private fun getNetworkType(context: Context): String {val connectivityManager =
        ContextCompat.getSystemService(context, ConnectivityManager::class.java)
            ?: return "Unknown" // Handle the case where ConnectivityManager is not available

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return "Unknown"
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return "Unknown"
            return when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WiFi"
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Mobile"
                else -> "Unknown"
            }
        } else {
            // For older versions, use the deprecated activeNetworkInfo
            @Suppress("DEPRECATION")
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            @Suppress("DEPRECATION")
            return when (activeNetworkInfo?.type) {
                ConnectivityManager.TYPE_WIFI -> "WiFi"
                ConnectivityManager.TYPE_MOBILE -> "Mobile"
                else -> "Unknown"
            }
        }
    }

    /**
     * Retrieves the installerpackage name for the application.
     *
     * This function determines where the app was installed from (e.g., Google Play Store, Amazon Appstore, etc.).
     * It handles the deprecation of `getInstallerPackageName` by using the newer `getInstallSourceInfo` API
     * on Android30 (API level R) and above.
     *
     * @param context The application context.
     * @return The installer package name, or "Unknown" if the installer cannot be determined.
     */
    private fun getInstallSource(context: Context): String {
        val packageManager = context.packageManager
        val packageName = context.packageName

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Use getInstallSourceInfo for Android 11 (API level 30) and above
            try {
                val installSourceInfo =packageManager.getInstallSourceInfo(packageName)
                installSourceInfo.installingPackageName ?: "Unknown"
            } catch (e: PackageManager.NameNotFoundException) {
                // Handle the case where the package name is not found (should rarely happen)
                "Unknown"
            }
        } else {
            // Use getInstallerPackageName for older Android versions (deprecated but still functional)
            @Suppress("DEPRECATION")
            packageManager.getInstallerPackageName(packageName) ?: "Unknown"
        }
    }
}
