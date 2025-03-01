package com.sunil.app.data.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * A utility class for executing tasks ona single background thread, primarily for disk I/O operations.
 *
 * This class provides a simple way to offload tasks that might block the main thread, such as reading from
 * or writing to disk. It ensures that all tasks are executed sequentially on a dedicated background thread.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
class DiskExecutor : Executor {

    /**
     * The underlying single-threaded executor used to run tasks.
     *
     * This executor ensures that all tasks submitted to it are executed sequentially on a single background thread.
     */
    private val diskIOExecutor: Executor = Executors.newSingleThreadExecutor()

    /**
     * Executes the given [runnable] task on the background thread managed by this executor.
     *
     * @param runnable The task to be executed.
     */
    override fun execute(runnable: Runnable) {
        diskIOExecutor.execute(runnable)
    }
}

///**
// * Alternative implementation using Kotlin Coroutines.
// *
// * This demonstrates how to achieve the same functionality as DiskExecutor using Kotlin Coroutines,
// * which offer a more modern and flexible approach to asynchronous programming.
// *
// *
// */
//
//class DiskExecutor(
//    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
//) {
//    private val scope = CoroutineScope(dispatcher)
//
//    /**
//     * Executes the given [block] task on the background thread managed by this executor.
//     *
//     * @param block The task to be executed.
//     */
//    fun execute(block: suspend () -> Unit) {
//        scope.launch {
//            block()
//        }
//    }
//}
