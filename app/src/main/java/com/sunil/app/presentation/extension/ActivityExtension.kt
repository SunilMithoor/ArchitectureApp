@file:JvmName("ActivityUtils")

package com.sunil.app.presentation.extension

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat


/*------------------------------------Activity---------------------------------------------*/


/**
 * Extension function for AppCompatActivity to hide the soft keyboard.
 *
 */
fun AppCompatActivity.hideSoftKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let { view ->
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    } ?: run {
        imm.hideSoftInputFromWindow(View(this).windowToken, 0)
    }
}

/**
 * Extension function for AppCompatActivity to show the soft keyboard.
 *
 * This function ensures that the soft keyboard is displayed when called.
 * It's designed to be used when you need to explicitly show the keyboard,
 * such as when an EditText gains focus.
 *
 * @param view The view that has focus and should receive input. If null, it will try to find the currently focused view.
 *             If no view is focused, the keyboard might not show.
 */
fun AppCompatActivity.showSoftKeyboard(view: View? = null) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        ?: return // Return early if InputMethodManager is not available

    // If a specific view is provided, use it. Otherwise, try to get the currently focused view.
    val focusedView = view ?: currentFocus
    if (focusedView != null) {
        // `RestfulRequest.kt` focus if the view doesn't have it already.
        if (!focusedView.hasFocus()) {
            focusedView.requestFocus()
        }
        // Show the soft keyboard for the focused view.
        inputMethodManager.showSoftInput(focusedView, InputMethodManager.SHOW_IMPLICIT)
    } else {
        // If no view is focused, we can still try to show the keyboard, but it might not be associated with any input field.
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}


/**
 * Registers an activity result launcher for an activity of type [T] with an optional shared element transition.
 *
 * @param sharedElement Optional pair of a [View] and its transition name for a shared element transition.
 * @param intentBuilder Optional lambda to configure the [Intent] before starting the activity.
 * @param onResult The callback to receive the result.
 * @return The [ActivityResultLauncher] to launch the activity.
 */
inline fun <reified T : AppCompatActivity> AppCompatActivity.registerStartActivityForResult(
    sharedElement: kotlin.Pair<View, String>? = null,
    noinline intentBuilder: (Intent.() -> Unit)? = null,
    crossinline onResult: (resultCode: Int, data: Intent?) -> Unit
): ActivityResultLauncher<Intent> {
    val contract = ActivityResultContracts.StartActivityForResult()
    val launcher = registerForActivityResult(contract) { result ->
        onResult(result.resultCode, result.data)
    }

    return launcher.apply {
        val intent = Intent(this@registerStartActivityForResult, T::class.java)
        intentBuilder?.invoke(intent)

//        val options: Bundle? = sharedElement?.let {
//            ActivityOptionsCompat.makeSceneTransitionAnimation(
//                this@registerStartActivityForResult,
//                it.first,
//                it.second
//            ).toBundle()
//        }

        val options: ActivityOptionsCompat? = sharedElement?.let {
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@registerStartActivityForResult,
                it.first,
                it.second
            )
        }

        launch(intent, options)
    }
}
