package com.sunil.app.data.remote.retrofit.datasource

import com.sunil.app.data.utils.NetworkAPIInvoke
import com.sunil.app.data.utils.NetworkHandler
import com.sunil.app.domain.model.IOTaskResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

abstract class ApiWrapper {

    companion object {
        private const val TAG = "ApiWrapper"
    }

    @Inject
    lateinit var networkHandler: NetworkHandler

    @Inject
    lateinit var applicationContext: ApplicationContext


    protected suspend fun <T : Any> getResult(
        allowRetries: Boolean = false, numberOfRetries: Int = 0, networkApiCall: NetworkAPIInvoke<T>
    ): Flow<IOTaskResult<T>> {
        var delayDuration = 1000L
        val delayFactor = 2
        val maxDelay = 8000L
        return flow {
            if (!networkHandler.isOnline()) {
                emit(IOTaskResult.OnFailed(IOException("No internet connection available.")))
                return@flow
            }
            val response = networkApiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(IOTaskResult.OnSuccess(it))
                } ?: emit(IOTaskResult.OnFailed(IOException("Response body is empty.")))
                return@flow
            }
            emit(
                IOTaskResult.OnFailed(
                    IOException("HTTP ${response.code()}: ${response.message()}")
                )
            )
            return@flow
        }.catch { e ->
            emit(IOTaskResult.OnFailed(IOException("Exception: ${e.localizedMessage}", e)))
            return@catch
        }.retryWhen { cause, attempt ->
            if (!allowRetries || attempt > numberOfRetries || cause !is IOException) return@retryWhen false
            // Log retry attempt
            Timber.tag(TAG).w("Retrying API call - Attempt $attempt due to ${cause.localizedMessage}")
            delay(delayDuration)
            delayDuration = (delayDuration * delayFactor).coerceAtMost(maxDelay)
            return@retryWhen true
        }.flowOn(Dispatchers.IO)
    }

}
