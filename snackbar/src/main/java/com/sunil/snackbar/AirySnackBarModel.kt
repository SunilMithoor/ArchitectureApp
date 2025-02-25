package com.sunil.snackbar

import android.view.View

/**
 * Represents the configuration for an AirySnackBar.
 ** This data class holds the necessary information to customize and display an AirySnackBar.
 *
 * @property anchorView The view to which the SnackBar will be anchored. If null, the SnackBar will be displayed at the bottom of the screen.
 * @property layoutAttributes A list of attributes to customize theSnackBar's layout.
 * @property associatedView The view associated with the SnackBar. This might be the view that triggered the SnackBar or a view that the SnackBar interacts with.
 */
data class AirySnackBarModel(
    var view: View? = null,
    var snackBarLayoutAttribute: MutableList<AirySnackBarLayoutAttribute> = mutableListOf(),
    var anchorView: View? = null
)
