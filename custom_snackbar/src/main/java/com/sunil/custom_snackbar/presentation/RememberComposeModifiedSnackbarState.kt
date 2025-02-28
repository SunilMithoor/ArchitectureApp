package com.sunil.custom_snackbar.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.sunil.custom_snackbar.util.ComposeModifiedSnackbarState

@Composable
fun rememberComposeModifiedSnackbarState(): ComposeModifiedSnackbarState {
    return remember { ComposeModifiedSnackbarState() }
}
