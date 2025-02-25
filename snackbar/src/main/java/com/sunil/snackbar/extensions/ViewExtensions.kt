package com.sunil.snackbar.extensions

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout


/**
 * Finds a suitable parent ViewGroup for displaying UI elements like Snack bars.
 *
 * This function traverses up the view hierarchy, starting from the given view,
 * to find the most appropriate parent for attaching UI elements. It prioritizes
 * CoordinatorLayout, then the content FrameLayout,and falls back to the last
 * encountered FrameLayout if neither is found.
 *
 * @return The most suitable parent ViewGroup, or null if no suitable parent is found.
 */
fun View?.findSuitableParent(): ViewGroup? {
    // Start from the given view, or return null if it's null.
    var currentView = this ?: return null

    // Keep track of the last FrameLayout encountered as a fallback.
    var fallback: FrameLayout? = null

    // Traverse up the view hierarchy until the root is reached or a suitable parent is found.
    do {
        when (currentView) {
            is ConstraintLayout -> {
                // ConstraintLayout is the preferred parent.
                return currentView
            }

            is FrameLayout -> {
                // Check if it's the content FrameLayout.
                if (currentView.id == android.R.id.content) {
                    return currentView
                } else {
                    // Store it as a fallback.
                    fallback = currentView
                }
            }
        }

        // Move up to the parent view.
        currentView = currentView.parent as? View ?: break
    } while (true)

    // Return the fallback FrameLayout or null if none was found.
    return fallback
}
