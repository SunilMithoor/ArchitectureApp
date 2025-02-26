package com.sunil.custom_snackbar.util

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ComposeModifiedSnackbarState {

    private val _message = mutableStateOf<String?>(null)
    val message: State<String?> = _message

    var updateState by mutableStateOf(false)
        private set

    fun showSnackbar(message: String) {
        _message.value = message
        updateState = !updateState
    }

    fun isNotEmpty(): Boolean {
        return _message.value != null
    }
}
