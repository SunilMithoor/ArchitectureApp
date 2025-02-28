package com.sunil.app.data.repository.onboarding

import com.sunil.app.data.local.sharedprefs.datasource.OnBoardingLocalDataSource
import com.sunil.app.domain.repository.onboarding.OnBoardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor(
    private val onBoardLocalDataSource: OnBoardingLocalDataSource,
) :
    OnBoardingRepository {

    override suspend fun getDarkMode(): Flow<Boolean> =
        onBoardLocalDataSource.getDarkMode()

    override suspend fun setDarkMode(value: Boolean): Flow<Boolean> =
        onBoardLocalDataSource.setDarkMode(value)

}
