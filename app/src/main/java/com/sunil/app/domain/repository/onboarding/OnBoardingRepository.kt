package com.sunil.app.domain.repository.onboarding


import kotlinx.coroutines.flow.Flow


interface OnBoardingRepository {

    suspend fun getDarkMode(): Flow<Boolean>

    suspend fun setDarkMode(value: Boolean): Flow<Boolean>

}
