package com.sunil.app.data.module

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppBaseUrl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppBaseUrl2

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppBaseUrl3

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Client

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppSettingsSharedPreference
