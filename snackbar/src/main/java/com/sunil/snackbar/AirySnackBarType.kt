package com.sunil.snackbar

import androidx.annotation.ColorRes


sealed interface AirySnackBarType

/**
 * Represents the different types of AirySnackbar that can be displayed.
 * This sealed interface ensures that all possible types are known at compile time,
 * allowing for exhaustive `when` expressions and preventing unexpected types.
 */

sealed class Type : AirySnackBarType {
    /**
     * Represents a successful operation or state.
     */
    data object Success : Type()

    /**
     * Represents an error or failure state.
     */
    data object Error : Type()

    /**
     * Represents an informational message.
     */
    data object Info : Type()

    /**
     * Represents a warning message.
     */
    data object Warning : Type()

    /**
     * Represents a default or neutral message.
     */
    data object Default : Type()

    /**
     * Represents a custom snackbar with a specified background color.
     ** @property bgColor The background color resource ID.
     */
    data class Custom(@ColorRes val bgColor: Int) : Type()
}
