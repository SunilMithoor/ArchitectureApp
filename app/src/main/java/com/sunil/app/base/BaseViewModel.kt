package com.sunil.app.base

import androidx.lifecycle.ViewModel
import com.sunil.app.presentation.extension.AppString
import com.sunil.app.presentation.util.ResourceProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

/**
 * Base ViewModel for managing common UI state and handling exceptions.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-02-28
 */
abstract class BaseViewModel() : ViewModel() {

    companion object {
        private const val TAG = "BaseViewModel"
    }

    @Inject
    lateinit var resourceProvider: ResourceProvider

    /** Holds UI messages as a state flow */
    val _message = MutableStateFlow("")
    val messageStateFlow: StateFlow<String> get() = _message

    /** Holds resource ID for UI messages */
    val _messageResId = MutableStateFlow(-1)
    val messageResIdStateFlow: StateFlow<Int> get() = _messageResId

    /** Exception handler for coroutine errors */
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.tag(TAG).e(throwable) // Log the error
        _message.value = resourceProvider.getString(AppString.internet_too_slow) // Update UI message
    }

    /** Coroutine scope with an exception handler */
    val coroutineScope = CoroutineScope(Dispatchers.IO + exceptionHandler)
}
