package com.sunil.app.base

import android.app.Application
import com.sunil.app.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeTimber()
        appInstance = this
        Timber.tag(TAG).d("Application created")
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.tag(TAG).d("Timber initialized")
        }
    }

    companion object {
        // Use constants for better readability and maintainability
        private const val TAG = "AppApplication"

        // Use a backing property for better control over access and modification
        private var _appInstance: AppApplication? = null

        @get:Synchronized
        var appInstance: AppApplication? = null
            get() = _appInstance ?: throw IllegalStateException("AppApplication not initialized")

        // Use a backing property for better control over access and modification
        private var _isActivityVisible = false
        val isActivityVisible: Boolean
            get() = _isActivityVisible

        // Use a backing property for better control over access and modification
        private var _isFirstLaunch = true
        val isFirstLaunch: Boolean
            get() = _isFirstLaunch

        fun onActivityResumed() {
            _isActivityVisible = true
        }

        fun onActivityPaused() {
            _isFirstLaunch = false
            _isActivityVisible = false
        }
    }
}
