package com.sunil.custom_snackbar.util

sealed class ComposeModifierSnackbarDuration(val time: Long) {
    data object LONG : ComposeModifierSnackbarDuration(10000L)
    data object SHORT : ComposeModifierSnackbarDuration(3000L)
    data object INFINITE : ComposeModifierSnackbarDuration(6000000L)
}
