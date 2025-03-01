package com.sunil.app.data.repository.onboarding

import com.sunil.app.data.local.sharedprefs.datasource.OnBoardingLocalDataSource
import com.sunil.app.domain.repository.onboarding.OnBoardingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of the [OnBoardingRepository] interface.
 *
 * This class handles data operations related to onboarding preferences, such as dark mode.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
class OnBoardingRepositoryImpl @Inject constructor(
    private val onBoardLocalDataSource: OnBoardingLocalDataSource,
) :
    OnBoardingRepository {

    /**
     * Retrieves the current dark mode preference.
     *
     * This function returns a [Flow] that emits the current dark mode setting (true for dark mode, false for light mode).
     * The operation is performed on the [Dispatchers.IO] dispatcher to avoid blocking the main thread.
     *
     * @return A [Flow] emitting the dark mode preference as a [Boolean].
     */
    override suspend fun getDarkMode(): Flow<Boolean> =
        onBoardLocalDataSource.getDarkMode()

    /**
     * Sets the dark mode preference.
     *
     * This function sets the dark mode preference to the given [value] and emits the same value through a [Flow].
     * The operation is performed on the [Dispatchers.IO] dispatcher to avoid blocking the main thread.
     *
     * @param value The new dark mode preference (true for dark mode, false for light mode).
     * @return A [Flow] emitting the set dark mode preference as a [Boolean].
     */
    override suspend fun setDarkMode(value: Boolean): Flow<Boolean> =
        onBoardLocalDataSource.setDarkMode(value)

}
