package com.sunil.app.base

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.sunil.app.BuildConfig
import com.sunil.app.presentation.workers.SyncMoviesWorker
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject


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
class AppApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var workManager: WorkManager

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    val imageLoader: ImageLoader by lazy {
        newImageLoader(this, BuildConfig.DEBUG)
    }

    override fun onCreate() {
        super.onCreate()
        initializeTimber()
        // Initialize appInstance here, ensuring it's set immediately after creation.
        _appInstance = this
        Timber.tag(TAG).d("Application created")

        enqueueSyncMoviesWorker()

        // Set the ImageLoader as the default for Coil
        Coil.setImageLoader(imageLoader)
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

    /*** Enqueues the SyncMoviesWorker to run periodically.
     */
    private fun enqueueSyncMoviesWorker() {
        workManager.enqueueUniqueWork(
            SyncMoviesWorker.WORK_NAME,
            ExistingWorkPolicy.KEEP,
            SyncMoviesWorker.getSyncMoviesWorkRequest()
        )
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

    /**
     * Creates and configures an [ImageLoader] instance for efficient image loading.
     *
     * This function provides a centralized way to create an [ImageLoader] with
     * optimized caching and logging configurations.
     *
     * @param context The application context.
     * @param isDebugMode A flag indicating whether the application is in debug mode.
     * @return A configured [ImageLoader] instance.
     */
    private fun newImageLoader(context: Context, isDebugMode: Boolean): ImageLoader {
        val memoryCachePercent = 0.25 // Increased memory cache percentage
        val diskCachePercent = 0.1 // Reduced disk cache percentage
        val maxMemoryCacheSize = (Runtime.getRuntime().maxMemory() * memoryCachePercent).toLong()
        val maxDiskCacheSize = (1024 * 1024 * 100).toLong() // 100MB

        val logger: DebugLogger? = if (isDebugMode) DebugLogger() else null

        return ImageLoader.Builder(context)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(memoryCachePercent)
                    .maxSizeBytes(maxMemoryCacheSize.toInt())
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(diskCachePercent)
                    .maxSizeBytes(maxDiskCacheSize)
                    .directory(context.cacheDir.resolve("image_cache")) // Use a specific directory
                    .build()
            }
            .logger(logger)
            .respectCacheHeaders(false) // Disable respecting cache headers
            .build()
    }

}
