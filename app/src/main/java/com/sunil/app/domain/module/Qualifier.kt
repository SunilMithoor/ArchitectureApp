@file:Suppress("MatchingDeclarationName", "Filename")

package com.sunil.app.domain.module

import javax.inject.Qualifier


/**
 * Dispatcher Qualifiers for dependency injection.
 *
 * These annotations are used to differentiate between different types of dispatchers
 * when injecting them into classes.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-02-16
 */

/**
 * Qualifier annotation for the IO dispatcher.
 *
 * This dispatcher is optimized for I/O-bound tasks.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
annotation class IoDispatcher

/**
 * Qualifier annotation for the Main dispatcher.
 *
 * This dispatcher is used for UI-related tasks and runs on the main thread.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
annotation class MainDispatcher

/**
 * Qualifier annotation for the Default dispatcher.
 *
 * This dispatcher is used for CPU-bound tasks and runs on a shared pool of background threads.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
annotation class DefaultDispatcher
