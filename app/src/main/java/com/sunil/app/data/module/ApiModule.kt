package com.sunil.app.data.module

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sunil.app.BuildConfig
import com.sunil.app.data.local.sharedprefs.AppSharedPreferences
import com.sunil.app.data.remote.retrofit.api.ApiAppBaseUrl1Service
import com.sunil.app.data.remote.retrofit.api.ApiAppBaseUrl2Service
import com.sunil.app.data.remote.retrofit.api.ApiAppBaseUrl3Service
import com.sunil.app.data.utils.NetworkHandler
import com.sunil.app.data.utils.TokenAuthenticator
import com.sunil.app.data.utils.UserAgentInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * ApiModule: Provides dependencies related to network communication using Retrofit and OkHttp.
 *
 * This module configures and provides instances of Retrofit, OkHttpClient, Gson, and other
 * network-related components for dependency injection.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val TAG = "ApiModule"
    private const val CLIENT_TIME_OUT_SECONDS = 30L // Timeout for network requests in seconds

    // --- Retrofit Instances ---

    /**
     * Provides a Retrofit instance configured for the first base URL.
     *
     * @param okHttpClient The OkHttpClient instance to use for network requests.
     * @param gson The Gson instance to use for JSON serialization/deserialization.* @return A Retrofit instance for the first base URL.
     */
    @Provides
    @Singleton
    @AppBaseUrl1
    fun provideRetrofitAppBaseUrl1(@Client okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return createRetrofit(BuildConfig.BASE_URL_1, okHttpClient, gson)
    }

    /**
     * Provides a Retrofit instance configured for the second base URL.
     *
     * @param okHttpClient The OkHttpClient instance to use for network requests.
     * @param gson The Gson instance to use for JSON serialization/deserialization.
     * @return A Retrofit instance for the second base URL.
     */
    @Provides
    @Singleton
    @AppBaseUrl2
    fun provideRetrofitAppBaseUrl2(@Client okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return createRetrofit(BuildConfig.BASE_URL_2, okHttpClient, gson)
    }

    /**
     * Provides a Retrofit instance configured for the third base URL.
     *
     * @param okHttpClient The OkHttpClient instance to use for network requests.
     * @param gson The Gson instance to use for JSON serialization/deserialization.
     * @return A Retrofit instance for the third base URL.
     */
    @Provides
    @Singleton
    @AppBaseUrl3
    fun provideRetrofitAppBaseUrl3(@Client okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return createRetrofit(BuildConfig.BASE_URL_3, okHttpClient, gson)
    }

    /**
     * Creates a Retrofit instance with the specified base URL, OkHttpClient, and Gson.
     *
     * @param baseUrl The base URL for the Retrofit instance.
     * @param okHttpClient The OkHttpClient instance to use for network requests.
     * @param gson The Gson instance to use for JSON serialization/deserialization.
     * @return A configured Retrofit instance.
     */
    private fun createRetrofit(baseUrl: String, okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        Timber.tag(TAG).d("Building Retrofit for: $baseUrl")
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson)) // Use provided Gson instance
            .build()
    }

    // --- OkHttpClient ---

    /**
     * Provides an OkHttpClient instance with configured interceptors and timeouts.
     *
     * @param context The application context.
     * @param loggingInterceptor The HttpLoggingInterceptor for logging network requests.
     * @param chuckerInterceptor The Chucker Interceptor for inspecting network traffic.
     * @param appSharedPreferences The AppSharedPreferences instance for storing and retrieving data.
     * @return A configured OkHttpClient instance.
     */
    @Provides
    @Singleton
    @Client
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        loggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        appSharedPreferences: AppSharedPreferences,
        networkHandler: NetworkHandler
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(CLIENT_TIME_OUT_SECONDS, TimeUnit.SECONDS) // Set connection timeout
            .readTimeout(CLIENT_TIME_OUT_SECONDS, TimeUnit.SECONDS) // Set read timeout
            .addInterceptor(
                UserAgentInterceptor(
                    appSharedPreferences,
                    networkHandler,
                    context
                )
            ) // Add User-Agent interceptor
            .authenticator(
                TokenAuthenticator(
                    appSharedPreferences,
                    context
                )
            ) // Add token authenticator

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(chuckerInterceptor) // Add Chucker interceptor in debug mode
            builder.addInterceptor(loggingInterceptor) // Add logging interceptor in debug mode
        }

        return builder.build()
    }

    // --- Interceptors ---

    /**
     * Provides an HttpLoggingInterceptor for logging network requests and responses.
     *
     * @return A configured HttpLoggingInterceptor instance.
     */
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        Timber.tag(TAG).d("Providing Logging Interceptor")
        return HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY // Log request and response bodies
            level = HttpLoggingInterceptor.Level.BASIC // Log request and response bodies
        }
    }

    /**
     * Provides a Chucker Interceptor for inspecting network traffic.
     *
     * @param context The application context.
     * @return A configured Chucker Interceptor instance.
     */
    @Provides
    @Singleton
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        Timber.tag(TAG).d("Providing Chucker Interceptor")
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true, // Show notification when Chucker is recording
            retentionPeriod = RetentionManager.Period.ONE_HOUR // Retain data for one hour
        )
        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(Long.MAX_VALUE) // Capture larger responses
            .alwaysReadResponseBody(true) // Always read the response body
            .build()
    }


    // --- Gson ---

    /**
     * Provides a Gson instance for JSON serialization and deserialization.
     *
     * @return A Gson instance.
     */
    @Provides
    @Singleton
    fun provideGson(): Gson {
        Timber.tag(TAG).d("Providing Gson")
        return Gson()
    }


    // --- API Services ---

    /**
     * Provides an ApiAppBaseUrl1Service instance for interacting with the first API.
     *
     * @param retrofit The Retrofit instance configured for the first base URL.
     * @return An ApiAppBaseUrl1Service instance.
     */
    @Provides
    @Singleton
    fun provideApiAppBaseUrl1Service(@AppBaseUrl1 retrofit: Retrofit): ApiAppBaseUrl1Service {
        Timber.tag(TAG).d("Providing ApiAppBaseUrl1Service")
        return retrofit.create(ApiAppBaseUrl1Service::class.java)
    }

    /**
     * Provides an ApiAppBaseUrl2Service instance for interacting with the second API.
     *
     * @param retrofit The Retrofit instance configured for the second base URL.
     * @return An ApiAppBaseUrl2Service instance.
     */
    @Provides
    @Singleton
    fun provideApiAppBaseUrl2Service(@AppBaseUrl2 retrofit: Retrofit): ApiAppBaseUrl2Service {
        Timber.tag(TAG).d("Providing ApiAppBaseUrl2Service")
        return retrofit.create(ApiAppBaseUrl2Service::class.java)
    }

    /**
     * Provides an ApiAppBaseUrl3Service instance for interacting with the third API.
     *
     * @param retrofit The Retrofit instance configured for the third base URL.
     * @return An ApiAppBaseUrl3Service instance.
     */

    @Provides
    @Singleton
    fun provideApiAppBaseUrl3Service(@AppBaseUrl3 retrofit: Retrofit): ApiAppBaseUrl3Service {
        Timber.tag(TAG).d("Providing ApiAppBaseUrl3Service")
        return retrofit.create(ApiAppBaseUrl3Service::class.java)
    }
}
