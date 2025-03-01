package com.sunil.app.data.module

import javax.inject.Qualifier


/**
 * Qualifier for the first application base URL.
 *
 * Use this annotation to inject dependencies that are specific to the first base URL.
 *
 * @see AppBaseUrl1
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppBaseUrl1

/**
 * Qualifier for the second application base URL.
 *
 * Use this annotation to inject dependencies that are specific to the second base URL.
 *
 * @see AppBaseUrl2
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppBaseUrl2

/**
 * Qualifier for the third application base URL.
 *
 * Use this annotation to inject dependencies that are specific to the third base URL.
 *
 * @see AppBaseUrl3
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppBaseUrl3

/**
 * Qualifier for the OkHttpClient instance.
 *
 * Use this annotation to inject the configured OkHttpClient instance.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Client

/**
 * Qualifier for the AppSettings SharedPreferences instance.
 *
 * Use this annotation to inject the SharedPreferences instance used for app settings.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppSettingsSharedPreference
