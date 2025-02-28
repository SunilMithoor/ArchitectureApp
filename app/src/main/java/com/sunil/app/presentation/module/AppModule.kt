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

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDiskExecutor(): DiskExecutor {
        return DiskExecutor()
    }

    @Singleton
    @Provides
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): WorkManager = WorkManager.getInstance(context)

    @Singleton
    @Provides
    fun provideCodeSnippet(
        @ApplicationContext context: Context
    ): CodeSnippet {
        return CodeSnippet(context)
    }
}
