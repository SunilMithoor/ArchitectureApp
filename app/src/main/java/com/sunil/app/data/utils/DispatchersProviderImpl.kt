package com.sunil.app.data.utils


import com.sunil.app.domain.utils.DispatchersProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher

/**
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 **/
class DispatchersProviderImpl(
    override val io: CoroutineDispatcher,
    override val main: MainCoroutineDispatcher,
    override val default: CoroutineDispatcher
) : DispatchersProvider
