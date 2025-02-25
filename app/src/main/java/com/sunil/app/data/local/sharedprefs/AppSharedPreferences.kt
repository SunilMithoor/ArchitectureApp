package com.sunil.app.data.local.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sunil.app.data.utils.Constants.USER_LOGGED_IN
import com.sunil.app.data.utils.Constants.USER_TOKEN
import timber.log.Timber


class AppSharedPreferences(context: Context, private val gson: Gson) {

    companion object {
        const val TAG = "AppSharedPreferences"
        const val PREF_NAME = "app_preferences" // Consider a more descriptive name
        const val USER_DATA = "user_data" // Example for storing user data as JSON
    }

    @PublishedApi
    internal val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    /**
     *
     * Also, using `inline` with `reified` is good for type safety, but it's not always necessary.
     * If you're not frequently using this function, consider removing `inline` to reduce code size.
     * Explanation: Using TypeToken from Gson allows you to handle generic types correctly.
     * This is crucial when dealing with collections or complex objects.
     */

    fun <T : Any> getObject(key: String): T? {
        val json = getJsonString(key) ?: return null
        return try {
            gson.fromJson(json, object : TypeToken<T>() {}.type)
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Error parsing JSON for key: $key")
            null
        }
    }

    @PublishedApi
    internal fun getJsonString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }


    /**
     * Explanation: Wrap the JSON serialization in a try-catch block to handle potential exceptions.
     */
    fun <T : Any> saveObject(key: String, model: T) {
        try {
            val jsonData = gson.toJson(model)
            putValue(key, jsonData)
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Error serializing object to JSON for key: $key")
        }
    }


    fun putValue(key: String, value: Any) {
        sharedPreferences.edit().apply {
            when (value) {
                is String -> putString(key, value)
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                else -> {
                    Timber.tag(TAG).e("Unsupported type for key: $key")
                    return
                }
            }
            apply() // Or commit() if you need immediate write
        }
    }


    fun <T : Any> getValue(key: String, clazz: Class<T>): T? {
        return sharedPreferences.run {
            when (clazz) {
                String::class.java -> getString(key, "")
                Boolean::class.java -> getBoolean(key, false)
                Float::class.java -> getFloat(key, -1f)
                Double::class.java -> {
                    val doubleString = getString(key, null)
                    if (doubleString != null) {
                        doubleString.toDoubleOrNull() ?: -1.0
                    } else {
                        -1.0
                    }
                }

                Int::class.java -> getInt(key, -1)
                Long::class.java -> getLong(key, -1L)
                else -> {
                    Timber.tag(TAG).e("Unsupported type for key: $key")
                    null
                }
            }
        } as T?
    }

    fun removeValue(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun clearUserSpecificData() {
        sharedPreferences.edit().apply {
            remove(USER_TOKEN)
            remove(USER_LOGGED_IN)
            apply()
        }
    }

    fun clearAllData() {
        sharedPreferences.edit().clear().apply()
    }

    fun logoutUser() {
        clearAllData()
        //open login intent
    }

}
