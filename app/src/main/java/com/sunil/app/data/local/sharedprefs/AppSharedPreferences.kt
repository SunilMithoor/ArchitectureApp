package com.sunil.app.data.local.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sunil.app.data.utils.Constants.USER_LOGGED_IN
import com.sunil.app.data.utils.Constants.USER_TOKEN
import timber.log.Timber
import kotlin.reflect.KClass


/**
 * A utility class for managing SharedPreferences, providing type-safe methods for storing and retrieving data.
 */
class AppSharedPreferences(context: Context, private val gson: Gson) {

    companion object {
        private const val TAG = "AppSharedPreferences"
        private const val PREF_NAME = "app_data_preferences" // More descriptive name
        private const val USER_DATA_KEY = "user_data" // Example for storing user data as JSON
        // Use constants from Constants class
        private const val DEFAULT_STRING_VALUE = ""
        private const val DEFAULT_BOOLEAN_VALUE = false
        private const val DEFAULT_FLOAT_VALUE = -1f
        private const val DEFAULT_DOUBLE_VALUE = -1.0
        private const val DEFAULT_INT_VALUE = -1
        private const val DEFAULT_LONG_VALUE = -1L
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    /**
     * Retrieves an object from SharedPreferences, deserializing it from JSON.
     *
     * @param key The key associated with the stored object.
     * @return The deserialized object, or null if the key is not found or an error occurs.
     */
    private inline fun <reified T : Any> getObject(key: String): T? {
        val json = getJsonString(key) ?: return null
        return try {
            gson.fromJson(json, object : TypeToken<T>() {}.type)
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Error parsing JSON for key: $key")
            null
        }
    }

    /**
     * Retrieves a JSON string from SharedPreferences.
     *
     * @param key The key associated with the stored JSON string.
     * @return The JSON string, or null if the key is not found.
     */
    private fun getJsonString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    /**
     * Saves an object to SharedPreferences, serializing it to JSON.
     *
     * @param key The key to associate with the object.
     * @param model The object to save.
     */
    fun <T : Any> saveObject(key: String, model: T) {
        try {
            val jsonData = gson.toJson(model)
            putValue(key, jsonData)
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Error serializing object to JSON for key: $key")
        }
    }

    /**
     * Stores a value of a supported type in SharedPreferences.
     *
     * @param key The key to associate with the value.
     * @param value The value to store.
     */
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
            apply() // Use apply for asynchronous write
        }
    }

    /**
     * Retrieves a value from SharedPreferences based on its type.
     *
     * @param key The key associated with the stored value.
     * @param clazz The KClass representing the expected type of the value.
     * @return The value, or a default value if the key is not found or the type is unsupported.
     */
    fun <T : Any> getValue(key: String, clazz: KClass<T>): T? {
        return sharedPreferences.run {
            when (clazz) {String::class -> getString(key, DEFAULT_STRING_VALUE)
                Boolean::class -> getBoolean(key, DEFAULT_BOOLEAN_VALUE)
                Float::class -> getFloat(key, DEFAULT_FLOAT_VALUE)
                Double::class -> {
                    val doubleString = getString(key, null)
                    doubleString?.toDoubleOrNull() ?: DEFAULT_DOUBLE_VALUE
                }
                Int::class -> getInt(key, DEFAULT_INT_VALUE)
                Long::class -> getLong(key, DEFAULT_LONG_VALUE)
                else -> {
                    Timber.tag(TAG).e("Unsupported type for key: $key")
                    null
                }
            }
        } as T?
    }

    /**
     * Removes a specific value from SharedPreferences.
     *
     * @param key The key associated with the value to remove.
     */
    fun removeValue(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    /**
     * Clears user-specific data from SharedPreferences.
     */
    fun clearUserSpecificData() {
        sharedPreferences.edit().apply {
            remove(USER_TOKEN)
            remove(USER_LOGGED_IN)
            apply()
        }
    }

    /**
     * Clears all data from SharedPreferences.
     */
    private fun clearAllData() {
        sharedPreferences.edit().clear().apply()
    }

    /**
     * Logs out the user by clearing all data and potentially navigating to the login screen.
     */
    fun logoutUser() {
        clearAllData()
        // TODO: Navigate to login screen (e.g., using an Intent)
    }
}
