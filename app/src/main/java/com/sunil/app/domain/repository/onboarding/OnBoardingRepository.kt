package com.sunil.app.domain.repository.onboarding


import kotlinx.coroutines.flow.Flow


interface OnBoardingRepository {

    /**
     * Retrieves the current dark mode setting.
     *
     * @return A [Flow] emitting the current dark mode state (true for dark mode, false for light mode).*/
    suspend fun getDarkMode(): Flow<Boolean>

    /**
     * Sets the dark mode setting.
     *
     * @param value The desired dark mode state (true for dark mode, false for light mode).
     * @return A [Flow] emitting the new dark mode state after it has been set.
     */
    suspend fun setDarkMode(value: Boolean): Flow<Boolean>
}
