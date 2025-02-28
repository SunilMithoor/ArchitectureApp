package com.sunil.app.data.utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
class DiskExecutor : Executor {

    private val executor: Executor = Executors.newSingleThreadExecutor()

    override fun execute(runnable: Runnable) {
        executor.execute(runnable)
    }
}
