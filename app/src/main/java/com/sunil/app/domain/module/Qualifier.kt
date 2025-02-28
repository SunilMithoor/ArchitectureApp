@file:Suppress("MatchingDeclarationName", "Filename")

package com.sunil.app.domain.module

import javax.inject.Qualifier

/**
 * @author Sunil
 * @version 1.0
 * @since 2025-02-16
 */

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultDispatcher
