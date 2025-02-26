package com.sunil.custom_snackbar.util

sealed class ComposeModifiedSnackbarPosition {

    data object Top : ComposeModifiedSnackbarPosition()

    data object Bottom : ComposeModifiedSnackbarPosition()

    data object Float : ComposeModifiedSnackbarPosition()
}
