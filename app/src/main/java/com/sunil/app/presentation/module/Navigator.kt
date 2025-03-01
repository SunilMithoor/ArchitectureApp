package com.sunil.app.presentation.module

import android.content.Context
import android.content.Intent
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator @Inject constructor() {

    init {
        Timber.d("Navigator init")
    }

    /**
     * Starts a new activity.
     *
     * @param context The context from which the activity is started.
     * @param activityClass The class of the activity to start.
     * @param flags Optional flags to set on the intent.
     * @param clearTask Whether to clear the task stack before starting the activity.
     * @param extras Optional extras to add to the intent.
     *
     * @throws IllegalArgumentException if the context is null.
     */
    fun startActivity(
        context: Context?,
        activityClass: Class<*>,
        flags: Int? = null,
        clearTask: Boolean = false,extras: Map<String, Any?> = emptyMap()
    ) {
        requireNotNull(context) { "Context cannot be null" }

        val intent = Intent(context, activityClass)

        // Apply flags
        if (clearTask) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        flags?.let { intent.flags = it }

        // Add extras
        extras.forEach { (key, value) ->
            when (value) {
                is String -> intent.putExtra(key, value)
                is Int -> intent.putExtra(key, value)
                is Boolean -> intent.putExtra(key, value)
                is Long -> intent.putExtra(key, value)
                is Double -> intent.putExtra(key, value)
                is Float -> intent.putExtra(key, value)
                is Char -> intent.putExtra(key, value)
                is Short -> intent.putExtra(key, value)
                is ByteArray -> intent.putExtra(key, value)
                is CharArray -> intent.putExtra(key, value)
                is IntArray -> intent.putExtra(key, value)
                is LongArray -> intent.putExtra(key, value)
                is FloatArray -> intent.putExtra(key, value)
                is DoubleArray -> intent.putExtra(key, value)
                is BooleanArray -> intent.putExtra(key, value)
                is ShortArray -> intent.putExtra(key, value)
                is CharSequence -> intent.putExtra(key, value)
                is android.os.Parcelable -> intent.putExtra(key, value)
                is java.io.Serializable -> intent.putExtra(key, value)
                null -> Timber.w("Extra with key '$key' is null and will be ignored.")
                else -> Timber.w("Extra with key '$key' and type ${value::class.java.name} is not supported and will be ignored.")
            }
        }

        context.startActivity(intent)
    }
}
