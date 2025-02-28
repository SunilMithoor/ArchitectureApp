package com.sunil.app.presentation.util

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CodeSnippet @Inject constructor(@ApplicationContext private val context: Context) {

    fun getContext(): Context {
        return context
    }

    fun getDrawable(drawableId: Int): Drawable? {
        return ContextCompat.getDrawable(context, drawableId)
    }

    fun getColor(colorId: Int): Int {
        return ContextCompat.getColor(context, colorId)
    }

    fun getString(stringId: Int): String {
        return context.getString(stringId)
    }

    fun getResources(): Resources {
        return context.resources
    }
}
