package com.sunil.app.domain.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher

interface DispatchersProvider {
    val io: CoroutineDispatcher
    val main: MainCoroutineDispatcher
    val default: CoroutineDispatcher
}
