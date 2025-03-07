package com.sunil.app.data.utils


import com.sunil.app.domain.model.Result
import kotlin.coroutines.cancellation.CancellationException

/**
 * Executes a suspendable API call within a try-catch block and returns the result wrapped in a [Result] sealed class.
 *
 * This function is designed to handle exceptions that might occur during an API call and provide a consistent way
 * to represent success or failure.*
 * @param apiCall A suspend function representing the API call to be executed.
 * @return A [Result] object wrapping either the successful result of the API call or an [Exception] if an error occurred.
 *         - [Result.Success] if the API call was successful.
 *         - [Result.Error] if an exception occurred during the API call.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> = try {
    Result.Success(apiCall.invoke()) // Invoke the API call and wrap the result in Result.Success
} catch (e: Exception) {
    if (e is CancellationException) {
        throw e // Re-throw CancellationException to allow coroutine cancellation
    }
    Result.Error(e) // Wrap the exception in Result.Error
}
