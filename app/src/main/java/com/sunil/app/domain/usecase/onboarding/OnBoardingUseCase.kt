package com.sunil.app.domain.usecase.onboarding


import com.sunil.app.domain.repository.onboarding.OnBoardingRepository
import com.sunil.app.domain.usecase.IUseCase
import com.sunil.app.domain.usecase.None
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GetDarkModeUseCase @Inject constructor(private val onBoardingRepo: OnBoardingRepository) :
    IUseCase<None, Flow<Boolean?>> {

    override suspend fun execute(input: None): Flow<Boolean> =
        onBoardingRepo.getDarkMode()
}

@Singleton
class SetDarkModeUseCase @Inject constructor(private val onBoardingRepo: OnBoardingRepository) :
    IUseCase<Boolean, Flow<Boolean>> {

    override suspend fun execute(input: Boolean): Flow<Boolean> =
        onBoardingRepo.setDarkMode(input)
}
