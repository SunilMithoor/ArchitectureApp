package com.sunil.snackbar

import android.app.Activity
import android.app.Dialog
import android.view.View

//sealed class AirySnackBarSource {
//    data class ActivitySource(val activity: Activity) : AirySnackBarSource()
//    data class DialogSource(val dialog: Dialog) : AirySnackBarSource()
//    data class ViewSource(val view: View) : AirySnackBarSource()
//}


sealed class AirySnackBarSource {
    abstract val contextDescription: String // Example of adding common behavior

    data class FromActivity(val activity: Activity) : AirySnackBarSource() {
        override val contextDescription: String = "Activity: ${activity.javaClass.simpleName}"
    }

    data class FromDialog(val dialog: Dialog) : AirySnackBarSource() {
        override val contextDescription: String = "Dialog: ${dialog.javaClass.simpleName}"
    }

    data class FromView(val view: View) : AirySnackBarSource() {
        override val contextDescription: String = "View: ${view.javaClass.simpleName}"
    }
}
