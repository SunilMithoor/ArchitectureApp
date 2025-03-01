package com.sunil.app.domain.usecase.onboarding


import com.sunil.app.domain.repository.onboarding.OnBoardingRepository
import com.sunil.app.domain.usecase.IUseCase
import com.sunil.app.domain.usecase.None
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Use case for retrieving the current dark mode setting.
 *
 * This class encapsulates the logic for fetching the dark mode preference
 * from the OnBoardingRepository.
 */
@Singleton
class GetDarkModeUseCase @Inject constructor(private val onBoardingRepository: OnBoardingRepository) :
    IUseCase<None, Flow<Boolean>> {

    override suspend fun execute(input: None): Flow<Boolean> {
        return onBoardingRepository.getDarkMode().map { it ?: false }
    }
}

/**
 * Use case for setting the dark mode preference.
 *
 * This class encapsulates the logic for setting the dark mode preference
 * in the OnBoardingRepository.
 */
@Singleton
class SetDarkModeUseCase @Inject constructor(private val onBoardingRepository: OnBoardingRepository) :
    IUseCase<Boolean, Flow<Boolean>> {

    override suspend fun execute(input: Boolean): Flow<Boolean> {
        return onBoardingRepository.setDarkMode(input)
    }
}
