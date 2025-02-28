package com.sunil.app.base

import android.app.Application
import com.sunil.app.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * AppApplication - The main application class for the app.
 *
 * This class is responsible for initializing Timber for logging,
 * providing a singleton-like access to the application instance,
 * and managing the application's lifecycle state (e.g., activity visibility, first launch).
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-02-28
 */
@HiltAndroidApp
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeTimber()
        // Initialize appInstance here, ensuring it's set immediately after creation.
        _appInstance = this
        Timber.tag(TAG).d("Application created")
    }

    /**
     * Initializes Timber for logging.
     *
     * Plants a DebugTree if the app is in debug mode.
     */
    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.tag(TAG).d("Timber initialized")
        }
    }

    companion object {
        // Use constants for better readability and maintainability
        private const val TAG = "AppApplication"

        // Backing property for appInstance.
        private var _appInstance: AppApplication? = null

        /**
         * Publicly accessible, read-only property for appInstance.
         *
         * @throws IllegalStateException if the AppApplication has not been initialized.
         */
        val instance: AppApplication
            get() = _appInstance ?: throw IllegalStateException("AppApplication not initialized")

        // Backing property for isActivityVisible, now private.
        private var _isActivityVisible = false

        /**
         * Publicly accessible, read-only property indicating if an activity is currently visible.
         */
        val isActivityVisible: Boolean
            get() = _isActivityVisible

        // Backing property for isFirstLaunch, now private.
        private var _isFirstLaunch = true

        /**
         * Publicly accessible, read-only property indicating if this is the first launch of the app.
         */
        val isFirstLaunch: Boolean
            get() = _isFirstLaunch

        /**
         * Called when an activity is resumed.
         *
         * Sets the _isActivityVisible flag to true.
         */
        fun onActivityResumed() {
            _isActivityVisible = true
        }

        /**
         * Called when an activity is paused.
         *
         * Sets the _isFirstLaunch flag to false and the _isActivityVisible flag to false.
         */
        fun onActivityPaused() {
            _isFirstLaunch = false
            _isActivityVisible = false
        }
    }
}
