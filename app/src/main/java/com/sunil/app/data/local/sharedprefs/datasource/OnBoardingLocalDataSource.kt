package com.sunil.app.data.local.sharedprefs.datasource

import com.sunil.app.data.local.sharedprefs.AppSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import kotlin.reflect.KClass


/**
 * OnBoardingLocalDataSource: Manages local data storage for onboarding and settings using SharedPreferences.
 *
 * This class provides an abstraction layer for accessing and modifying data stored in SharedPreferences,
 * specifically for onboarding-related preferences like dark mode.
 *
 * @property appSharedPreferences The underlying SharedPreferences wrapper for data persistence.
 *
 * @author Sunil
 * @since 1.0
 * @since 2025-01-28
 */
class OnBoardingLocalDataSource @Inject constructor(private val appSharedPreferences: AppSharedPreferences) {

    companion object {
        private const val DARK_MODE = "dark_mode"
    }

    /*** Retrieves a value from SharedPreferences.
     *
     * @param key The key associated with the value.
     * @param clazz The class of the value to retrieve.
     * @param defaultValue The default value to return if the key is not found or an error occurs.
     * @return A Flow emitting the retrieved value or the default value.
     */
    private fun <T : Any> getValue(key: String, clazz: KClass<T>, defaultValue: T): Flow<T> {
        return flow {
            val value = appSharedPreferences.getValue(key, clazz) ?: defaultValue
            emit(value)
        }.handleSharedPreferencesError(key, defaultValue)
    }

    /**
     * Sets a value in SharedPreferences.
     *
     * @param key The key to associate with the value.
     * @param value The value to store.
     * @return A Flow emitting true if the operation was successful, false otherwise.
     */
    private fun <T : Any> setValue(key: String, value: T): Flow<Boolean> {
        return flow {
            appSharedPreferences.putValue(key, value)
            emit(true)
        }.handleSharedPreferencesError(key, false)
    }

    /**
     * Handles errors that might occur during SharedPreferences operations.
     *
     * @param key The key associated with the operation.
     * @param defaultValue The default value to emit in case of an error.
     * @return A Flow that handles errors and emits the default value if an error occurs.
     */
    private fun <T> Flow<T>.handleSharedPreferencesError(key: String, defaultValue: T): Flow<T> {
        return this
            .catch { exception ->
                // Log the exception for debugging purposes. Consider using a proper logging library.
                println("Error with SharedPreferences for key '$key': ${exception.message}")
                emit(defaultValue) // Emit the default value in case of an error.
            }
            .flowOn(Dispatchers.IO)
            .onStart {
                // Optional: Emit a loading state if needed.
                // emit(Loading)
            }
    }

    /**
     * Retrieves the dark mode preference from SharedPreferences.
     *
     * @return A Flow emitting the dark mode state (true if dark mode is enabled, false otherwise).
     *         Emits false if an error occurs during retrieval.
     */
    fun getDarkMode(): Flow<Boolean> {
        return getValue(DARK_MODE, Boolean::class, false)
    }

    /**
     * Sets the dark mode preference in SharedPreferences.
     *
     * @param isDarkModeEnabled The desired dark mode state (true for enabled, false for disabled).
     * @return A Flow emitting true if the operation was successful, false otherwise.
     */
    fun setDarkMode(isDarkModeEnabled: Boolean): Flow<Boolean> {
        return setValue(DARK_MODE, isDarkModeEnabled)
    }

    /**
     * Sets the dark mode preference in SharedPreferences and returns the set value.
     *
     * @param isDarkModeEnabled The desired dark mode state (true for enabled, false for disabled).
     * @return A Flow emitting the set value if the operation was successful, null otherwise.
     */
    fun setDarkModeAndReturn(isDarkModeEnabled: Boolean): Flow<Boolean?> {
        return setValue(
            DARK_MODE,
            isDarkModeEnabled
        ).map { success -> if (success) isDarkModeEnabled else null }
    }

    /**
     * Retrieves the dark mode preference from SharedPreferences and maps it to a non-nullable Boolean.
     *
     * @return A Flow emitting the dark mode state (true if dark mode is enabled, false otherwise).
     */
    fun getDarkModeMapped(): Flow<Boolean> {
        return getValue(DARK_MODE, Boolean::class, false)
    }
}
