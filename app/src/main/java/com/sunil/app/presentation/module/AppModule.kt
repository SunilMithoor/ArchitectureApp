package com.sunil.app.presentation.module

import android.content.Context
import androidx.work.WorkManager
import com.sunil.app.data.utils.DiskExecutor
import com.sunil.app.presentation.util.CodeSnippet
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides application-wide dependencies.
 *
 * This module is installed in the [SingletonComponent], meaning the dependencies provided here
 * will be available throughout the application's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides a singleton instance of [DiskExecutor].
     *
     * [DiskExecutor] is used for executing tasks on a background thread.
     *
     * @return A singleton instance of [DiskExecutor].*/
    @Singleton
    @Provides
    fun provideDiskExecutor(): DiskExecutor = DiskExecutor()

    /**
     * Provides a singleton instance of [WorkManager].
     *
     * [WorkManager] is used for scheduling deferrable, asynchronous tasks.
     *
     * @param context The application context.
     * @return A singleton instance of [WorkManager].
     */
    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager =
        WorkManager.getInstance(context)

    /**
     * Provides a singleton instance of [CodeSnippet].
     *
     * [CodeSnippet] is a utility class that likely provides helper functions for the presentation layer.
     *
     * @param context The application context.
     * @return A singleton instance of [CodeSnippet].
     */
    @Singleton
    @Provides
    fun provideCodeSnippet(@ApplicationContext context: Context): CodeSnippet =
        CodeSnippet(context)
}
