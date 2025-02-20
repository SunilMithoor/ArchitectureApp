package com.sunil.app.base

import android.app.Application
import com.sunil.app.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class AppApplication : Application() {
    private var TAG: String = "AppApplication"

    init {
        activityFirstLaunch = true
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        initTimber()
    }


    private fun initTimber() {
        // This will initialize Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.tag(TAG).d("Timber initialized")
        }
        Timber.tag(TAG).d("Application created")
    }


    companion object {
        // return true or false
        var isActivityVisible: Boolean = false
        var activityFirstLaunch: Boolean = false // Variable that will check the

        @get:Synchronized
        var instance: AppApplication? = null
            private set

        fun activityResumed() {
            isActivityVisible = true // this will set true when activity resumed
        }

        fun activityPaused() {
            activityFirstLaunch = false
            isActivityVisible = false // this will set false when activity paused
        }
    }

}
