package com.sunil.app.presentation.util

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat
import java.util.regex.Pattern


class CodeSnippet(val context: Context?) {

    fun getContexts() : Context? {
        return context
    }

    fun getDrawables(drawable: Int) : Drawable?{
        return context?.let { ContextCompat.getDrawable(it, drawable) }
    }

    fun getColors(drawable: Int) : Int?{
        return context?.let { ContextCompat.getColor(it, drawable) }
    }

    fun getStrings(drawable: Int) : String?{
        return context?.getString(drawable)
    }

    fun getResources() : Resources? {
        return context?.resources
    }

}
