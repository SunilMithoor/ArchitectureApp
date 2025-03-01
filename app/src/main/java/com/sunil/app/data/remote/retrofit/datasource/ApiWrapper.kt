package com.sunil.app.data.remote.retrofit.datasource

import com.sunil.app.data.utils.NetworkAPIInvoke
import com.sunil.app.data.utils.NetworkHandler
import com.sunil.app.domain.model.IOTaskResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import kotlin.math.pow

//abstract class ApiWrapper {
//
//    companion object {
//        private const val TAG = "ApiWrapper"
//    }
//
//    @Inject
//    lateinit var networkHandler: NetworkHandler
//
//
//    protected suspend fun <T : Any> getResult(
//        allowRetries: Boolean = false, numberOfRetries: Int = 0, networkApiCall: NetworkAPIInvoke<T>
//    ): Flow<IOTaskResult<T>> {
//        var delayDuration = 1000L
//        val delayFactor = 2
//        val maxDelay = 8000L
//        return flow {
//            if (!networkHandler.isOnline()) {
//                emit(IOTaskResult.OnFailed(IOException("No internet connection available.")))
//                return@flow
//            }
//            val response = networkApiCall()
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(IOTaskResult.OnSuccess(it))
//                } ?: emit(IOTaskResult.OnFailed(IOException("Response body is empty.")))
//                return@flow
//            }
//            emit(
//                IOTaskResult.OnFailed(
//                    IOException("HTTP ${response.code()}: ${response.message()}")
//                )
//            )
//            return@flow
//        }.catch { e ->
//            emit(IOTaskResult.OnFailed(IOException("Exception: ${e.localizedMessage}", e)))
//            return@catch
//        }.retryWhen { cause, attempt ->
//            if (!allowRetries || attempt > numberOfRetries || cause !is IOException) return@retryWhen false
//            // Log retry attempt
//            Timber.tag(TAG).w("Retrying API call - Attempt $attempt due to ${cause.localizedMessage}")
//            delay(delayDuration)
//            delayDuration = (delayDuration * delayFactor).coerceAtMost(maxDelay)
//            return@retryWhen true
//        }.flowOn(Dispatchers.IO)
//    }
//
//}
//


/**
 * ApiWrapper is an abstract class that provides a common way to handle API calls.
 * It includes functionality for checking network connectivity, handling responses,
 * retrying failed requests, and managing errors.
 */
abstract class ApiWrapper {

    companion object {
        private const val TAG = "ApiWrapper"

        /**
         * The initial delay in milliseconds before the first retry attempt.
         */
        private const val INITIAL_RETRY_DELAY_MS = 1000L

        /**
         * The factor by which the delay increases with each retry attempt.
         */
        private const val RETRY_DELAY_FACTOR = 2

        /**
         * The maximum delay in milliseconds between retry attempts.
         */
        private const val MAX_RETRY_DELAY_MS = 8000L
    }

    /**
     * Injected NetworkHandler instance for checking network connectivity.
     */
    @Inject
    lateinit var networkHandler: NetworkHandler

    /**
     * Executes a network API call and handles the response.
     *
     * This function wraps a network API call, handles potential errors, and provides
     * retry functionality. It returns a Flow that emits the result of the API call.
     *
     * @param allowRetries Whether to allow retries in case of failure.
     * @param maxRetries The maximum number of retry attempts.
     * @param networkApiCall A lambda functionthat performs the network API call.
     * @return A Flow emitting an IOTaskResult, which can be either OnSuccess or OnFailed.
     */
    protected suspend fun <T : Any> getResult(
        allowRetries: Boolean = false,
        maxRetries: Int = 0,
        networkApiCall: NetworkAPIInvoke<T>
    ): Flow<IOTaskResult<T>> {
        return flow {
            // Check for network connectivity.
            if (!networkHandler.isOnline()) {
                emit(IOTaskResult.Failure(IOException("No internet connection available.")))
                return@flow
            }

            // Execute the network API call.
            val response: Response<T> = networkApiCall()

            // Handle the response.
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    // Emit the successful result.
                    emit(IOTaskResult.Success(body))
                } else {
                    // Emit an error if the response body is null.
                    emit(IOTaskResult.Failure(IOException("Response body is null.")))
                }
            } else {
                // Emit an error if the response is not successful.
                emit(
                    IOTaskResult.Failure(
                        IOException("HTTP ${response.code()}: ${response.message()}")
                    )
                )
            }
        }.catch { e ->
            // Handle exceptions during the API call.
            emit(IOTaskResult.Failure(IOException("Exception during API call: ${e.message}", e)))
        }.retryWhen { cause, attempt ->
            // Handle retry logic.
            handleRetry(cause, attempt, allowRetries, maxRetries)
        }.flowOn(Dispatchers.IO) // Execute the flow on the IO dispatcher.
    }

    /**
     * Handles the retry logic for API calls.
     *
     * This function determines whether a retry should be attempted based on the
     * cause of the failure, the number of attempts, and whether retries are allowed.
     *
     * @param cause The cause of the failure.
     * @param attempt The current retry attempt number.
     * @param allowRetries Whether retries are allowed.* @param maxRetries The maximum number of retry attempts.
     * @return True if a retry should be attempted, false otherwise.
     */
    private suspend fun handleRetry(
        cause: Throwable,
        attempt: Long,
        allowRetries: Boolean,
        maxRetries: Int
    ): Boolean {
        // Do not retry if retries are not allowed, the maximum number of retries has been reached, or the cause is not an IOException.
        if (!allowRetries || attempt > maxRetries || cause !is IOException) {
            return false
        }

        // Calculate the delay before the next retry attempt.
        val delayDuration = calculateDelay(attempt)

        // Log the retry attempt.
        Timber.tag(TAG)
            .w("Retrying API call - Attempt $attempt due to ${cause.message} after $delayDuration ms")
        // Delay the execution.
        delay(delayDuration)
        return true
    }

    /**
     * Calculates the delay before the next retry attempt using exponential backoff.
     *
     * @param attempt The current retry attempt number.
     * @return The delay in milliseconds.
     */
    private fun calculateDelay(attempt: Long): Long {
        // Exponential backoff: delay = initialDelay * (delayFactor ^ attempt)
        val exponentialDelay =
            INITIAL_RETRY_DELAY_MS * RETRY_DELAY_FACTOR.toDouble().pow(attempt.toDouble()).toLong()
        // Ensure the delay does not exceed the maximum delay.
        return exponentialDelay.coerceAtMost(MAX_RETRY_DELAY_MS)
    }
}
