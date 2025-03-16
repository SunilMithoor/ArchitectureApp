package com.sunil.app.domain.model


import com.sunil.app.domain.model.Result.Error
import com.sunil.app.domain.model.Result.Success


/**
 * Represents the result of an I/O task, either successful with data or failed with an exception.
 *
 * @param DTO The type of data returned on success.
 */
sealed class IOTaskResult<out DTO : Any> {
    /**
     * Represents a successful I/O operation.
     *
     * @param data The data returned by the operation.
     */
    data class Success<out DTO : Any>(val data: DTO) : IOTaskResult<DTO>()

    /**
     * Represents a failed I/O operation.
     *
     * @param throwable The exception that caused the failure.
     */
    data class Failure(val throwable: Throwable) : IOTaskResult<Nothing>()
}


/**
 * Represents the state of the UI, allowing for a controlled set of states.
 *
 * @param T The type of data associated with the success state.
 */
sealed class ViewState<out T : Any> {

    /**
     * Represents the UI state when an operation is in progress.
     *
     * @param isLoading True if the loading indicator should be shown, false otherwise.
     */
    data class Loading(val isLoading: Boolean) : ViewState<Nothing>()

    /**
     * Represents the UI state when an operation has completed successfully.
     *
     * @param data The data returned by the successful operation.
     */
    data class Success<out T : Any>(val data: T) : ViewState<T>()

    /**
     * Represents the UI state when an operation has failed.
     *
     * @param throwable The exception that caused the failure.
     */
    data class Failure(val throwable: Throwable) : ViewState<Nothing>()
}

/**
 * Represents the result of an operation, either successful with data or failed with an error.
 *
 * @param T The type of data returned on success.
 */
sealed class Result<out T> {
    /**
     * Represents a successful operation.
     *
     * @param data The data returned by the operation.*/
    data class Success<out T>(val data: T) : Result<T>()

    /**
     * Represents a failed operation.
     *
     * @param error The exception that caused the failure.
     */
    data class Error(val error: Throwable) : Result<Nothing>()
}

/**
 * Executes the given [block] if the [Result] is a [Success].
 *
 * @param block The function to execute with the data.
 * @return The original [Result].
 */
inline fun <T> Result<T>.onSuccess(
    block: (T) -> Unit
): Result<T> = if (this is Success) also { block(data) } else this

/**
 * Executes the given [block] if the [Result] is an [Error].
 *
 * @param block The function to execute with the error.
 * @return The original [Result].
 */
inline fun <T> Result<T>.onError(
    block: (Throwable) -> Unit
): Result<T> = if (this is Error) also { block(error) } else this

/**
 * Returns the data if the [Result] is a [Success], otherwise returns null.
 *
 * @return The data or null.
 */
fun <T> Result<T>.getOrNull(): T? = (this as? Success)?.data

/**
 * Transforms the data in a [Success] [Result] using the given [transform] function.
 *
 * @param transform The function to transform the data.
 * @return A new [Result] with the transformed data or the original [Error].
 */
inline fun <T, A> Result<T>.map(transform: (T) -> A): Result<A> {
    return when (this) {
        is Success -> Success(transform(data))
        is Error -> Error(error)
    }
}
