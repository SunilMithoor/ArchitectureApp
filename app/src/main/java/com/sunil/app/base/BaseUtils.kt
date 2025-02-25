package com.sunil.app.base

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.sunil.app.BuildConfig
import timber.log.Timber

private const val TAG = "AppUtils"

/**
 * Gets the app name
 *
 * @param context
 * @return appname
 */
fun getAppName(context: Context?): String {
    if (context == null) {
        Timber.tag(TAG).e("Context is null")
        return "App"
    }
    return runCatching {
        val packageManager: PackageManager = context.packageManager
        val applicationInfo = context.applicationInfo
        packageManager.getApplicationLabel(applicationInfo).toString()
    }.onFailure { e ->
        when (e) {
            is PackageManager.NameNotFoundException -> Timber.tag(TAG)
                .e(e, "Error getting application label")

            else -> Timber.tag(TAG).e(e, "Unexpected error getting application label")
        }
    }.getOrDefault("App")
}

/**
 * Gets the app version
 *
 * @param context
 * @return app version
 */
fun getAppVersion(context: Context?): String {
    if (context == null) {
        Timber.tag(TAG).e("Context is null")
        return "V 1.0.0"
    }
    return runCatching {
        val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.PackageInfoFlags.of(0)
            )
        } else {
            @Suppress("DEPRECATION")
            context.packageManager.getPackageInfo(context.packageName, 0)
        }

        val versionName = packageInfo.versionName ?: "Unknown"
        val flavor = BuildConfig.FLAVOR
        val formattedFlavor =when {
            flavor.equals("dev", ignoreCase = true) -> "Dev"
            flavor.equals("stage", ignoreCase = true) -> "Stage"
            else -> flavor.replaceFirstChar { it.uppercase() }
        }

        val formattedVersion = when {
            flavor.equals("dev", ignoreCase = true) || flavor.equals("stage", ignoreCase = true) -> "$formattedFlavor V $versionName"
            else -> "V $versionName"
        }
        formattedVersion
    }.onFailure { e ->
        when (e) {
            is PackageManager.NameNotFoundException -> Timber.tag(TAG)
                .e(e, "Failed to get package info")

            else -> Timber.tag(TAG).e(e, "Unexpected error getting app version")
        }
    }.getOrDefault("Unknown")
}
