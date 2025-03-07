package com.sunil.app.presentation.util

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


/**
 * A utility class for accessing resources from the application context.
 *
 * This class provides a centralized way to retrieve resources like drawables, colors, and strings,
 * ensuring consistency and simplifying resource access throughout the application.
 */
@Singleton
class ResourceProvider @Inject constructor(@ApplicationContext private val context: Context) {

    /**
     * Retrieves a drawable resource.
     *
     * @param drawableId The resource ID of the drawable. Must be a valid drawable resource ID.
     * @return The Drawable object, or null if the resource is not found.*/
    fun getDrawable(@DrawableRes drawableId: Int): Drawable? {
        return ContextCompat.getDrawable(context, drawableId)
    }

    /**
     * Retrieves a color resource.
     *
     * @param colorId The resource ID of the color. Must be a valid color resource ID.
     * @return The color as an integer.
     */
    fun getColor(@ColorRes colorId: Int): Int {
        return ContextCompat.getColor(context, colorId)
    }

    /**
     * Retrieves a string resource.
     *
     * @param stringId The resource ID of the string. Must be a valid string resource ID.
     * @return The string.
     */
    fun getString(@StringRes stringId: Int): String {
        return context.getString(stringId)
    }

    /**
     * Retrieves the application's resources.
     *
     * @return The Resources object.
     */
    fun getResources(): Resources {
        return context.resources
    }

    /**
     * Retrieves the application context.
     *
     * @return The application context.
     */
    fun getContext(): Context {
        return context
    }
}
