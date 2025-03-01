package com.sunil.app.data.module

import android.content.Context
import com.google.gson.Gson
import com.sunil.app.data.local.sharedprefs.AppSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Provides the AppSharedPreferences instance.
 *
 * @param context The application context.
 * @param gson The Gson instance for JSON serialization and deserialization.
 * @return An instance of AppSharedPreferences.
 */
@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
        gson: Gson
    ): AppSharedPreferences {
        return AppSharedPreferences(context, gson)
    }
}
