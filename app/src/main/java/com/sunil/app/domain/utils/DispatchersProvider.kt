package com.sunil.app.domain.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher


/**
 * Interface for providing CoroutineDispatchers.
 *
 * This interface abstracts the retrieval of different CoroutineDispatchers,
 * allowing for easier testing and dependency injection.
 */
interface DispatchersProvider {
    /**
     * Dispatcher for I/O-bound tasks.
     *
     * Use this dispatcher for tasks that involve blocking I/O operations,
     * such as network requests or file system access.
     */
    val io: CoroutineDispatcher

    /**
     * Dispatcher for UI-related tasks.
     *
     * Use this dispatcher for tasks that need to interact with the UI,
     * such as updating views or handling user input.
     */
    val main: MainCoroutineDispatcher

    /**
     * Dispatcher for CPU-intensive tasks.
     *
     * Use this dispatcher for tasks that involve heavy computation,
     * such as data processing or complex algorithms.
     */
    val default: CoroutineDispatcher

    /**
     * Dispatcher for unconfined tasks.
     *
     * Use this dispatcher for tasks that are not confined to a specific thread.
     * It is generally not recommended for most use cases, but can be useful in specific scenarios.
     */
    val unconfined: CoroutineDispatcher
}
