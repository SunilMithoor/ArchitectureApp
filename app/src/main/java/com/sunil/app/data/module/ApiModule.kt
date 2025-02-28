package com.sunil.app.data.module

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.Gson
import com.sunil.app.BuildConfig
import com.sunil.app.data.local.sharedprefs.AppSharedPreferences
import com.sunil.app.data.remote.retrofit.api.ApiAppBaseUrl1Service
import com.sunil.app.data.remote.retrofit.api.ApiAppBaseUrl2Service
import com.sunil.app.data.remote.retrofit.api.ApiAppBaseUrl3Service
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


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val TAG = "ApiModule"
    private const val CLIENT_TIME_OUT = 30L // Use Long for consistency and clarity

    @Provides
    @Singleton
    @AppBaseUrl1
    fun provideRetrofitAppBaseUrl1(@Client client: OkHttpClient): Retrofit {
        Timber.tag(TAG).d("Building Retrofit for App Base URL 1")
        return createRetrofit(BuildConfig.BASE_URL_1, client)
    }

    @Provides
    @Singleton
    @AppBaseUrl2
    fun provideRetrofitAppBaseUrl2(@Client client: OkHttpClient): Retrofit {
        Timber.tag(TAG).d("Building Retrofit for App Base URL 2")
        return createRetrofit(BuildConfig.BASE_URL_2, client)
    }

    @Provides
    @Singleton
    @AppBaseUrl3
    fun provideRetrofitAppBaseUrl3(@Client client: OkHttpClient): Retrofit {
        Timber.tag(TAG).d("Building Retrofit for App Base URL 3")
        return createRetrofit(BuildConfig.BASE_URL_3, client)
    }

    private fun createRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Client
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        loggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        appSharedPreferences: AppSharedPreferences
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(UserAgentInterceptor(appSharedPreferences, context))
            .authenticator(TokenAuthenticator(appSharedPreferences, context))
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(chuckerInterceptor)
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        Timber.tag(TAG).d("Providing Logging Interceptor")
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context,
    ): ChuckerInterceptor {
        Timber.tag(TAG).d("Providing Chucker Interceptor")
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(250_000L) // Use underscore for readability
            .alwaysReadResponseBody(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }


    @Provides
    @Singleton
    fun provideContext(): ApplicationContext {
        return ApplicationContext()
    }


    @Provides
    @Singleton
    fun provideApiAppBaseUrl1Service(@AppBaseUrl1 retrofit: Retrofit): ApiAppBaseUrl1Service {
        Timber.tag(TAG).d("Providing ApiAppBaseUrl1Service")
        return retrofit.create(ApiAppBaseUrl1Service::class.java)
    }

    @Provides
    @Singleton
    fun provideApiAppBaseUrl2Service(@AppBaseUrl2 retrofit: Retrofit): ApiAppBaseUrl2Service {
        Timber.tag(TAG).d("Providing ApiAppBaseUrl2Service")
        return retrofit.create(ApiAppBaseUrl2Service::class.java)
    }

    @Provides
    @Singleton
    fun provideApiAppBaseUrl3Service(@AppBaseUrl3 retrofit: Retrofit): ApiAppBaseUrl3Service {
        Timber.tag(TAG).d("Providing ApiAppBaseUrl3Service")
        return retrofit.create(ApiAppBaseUrl3Service::class.java)
    }
}
