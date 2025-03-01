package com.sunil.app.domain.utils

import com.sunil.app.domain.model.IOTaskResult
import com.sunil.app.domain.model.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * Util method that takes a suspend function returning a [Flow] of [IOTaskResult] as input param and returns a
 * [Flow] of [ViewState], which emits [ViewState.Loading] with true prior to performing the IO Task. If the
 * IO operation results a [IOTaskResult.Success], the result is mapped to a [ViewState.Success] instance and emitted,
 * else a [IOTaskResult.Failure] is mapped to a [ViewState.Failure] instance and emitted.
 * The flowable is then completed by emitting a [ViewState.Loading] with false
 */

suspend fun <T : Any> getViewStateFlowForNetworkCall(ioOperation: suspend () -> Flow<IOTaskResult<T>>) =
    flow {
        emit(ViewState.Loading(true))
        ioOperation().map {
            when (it) {
                is IOTaskResult.Success -> ViewState.Success(it.data)
                is IOTaskResult.Failure -> ViewState.Failure(it.throwable)
            }
        }.collect {
            emit(it)
        }
        emit(ViewState.Loading(false))
    }.flowOn(Dispatchers.IO)


suspend fun <T : Any> getViewStateFlowForAll(ioOperation: suspend () -> Flow<T>) =
    flow {
        emit(ViewState.Loading(true))
        ioOperation().map {
            it
        }.collect {
            emit(it)
        }
        emit(ViewState.Loading(false))
    }.flowOn(Dispatchers.IO)
