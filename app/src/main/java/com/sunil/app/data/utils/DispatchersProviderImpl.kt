package com.sunil.app.data.utils


import com.sunil.app.domain.utils.DispatchersProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher

/**
 * Concrete implementation of the [DispatchersProvider] interface.
 *
 * This class provides access to different coroutine dispatchers, allowing for
 * control over which thread or thread pool a coroutine runs on. It's designed
 * to be used with dependency injection, making it easy to swap out dispatchers* for testing or other purposes.
 *
 * @property io The [CoroutineDispatcher] for I/O-bound tasks.
 * @property main The [MainCoroutineDispatcher] for tasks that need to run on the main thread (UI thread).
 * @property default The [CoroutineDispatcher] for CPU-intensive tasks.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
class DispatchersProviderImpl(
    override val io: CoroutineDispatcher,
    override val main: MainCoroutineDispatcher,
    override val default: CoroutineDispatcher,
    override val unconfined: CoroutineDispatcher
) : DispatchersProvider
