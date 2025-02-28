package com.sunil.app.data.local.sharedprefs.datasource

import com.sunil.app.data.local.sharedprefs.AppSharedPreferences
import com.sunil.app.data.utils.AppConstants.DARK_MODE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OnBoardingLocalDataSource @Inject constructor(private val appSharedPreferences: AppSharedPreferences) {

    suspend fun getDarkMode(): Flow<Boolean> {
        return flow {
            val launch = appSharedPreferences.getValue(DARK_MODE, Boolean::class.java) ?: false
            emit(launch)
            return@flow
        }.catch {
            emit(false)
            return@catch
        }.flowOn(Dispatchers.IO)
    }

    suspend fun setDarkMode(value: Boolean): Flow<Boolean> {
        return flow {
            appSharedPreferences.putValue(DARK_MODE, value)
            emit(true)
            return@flow
        }.catch {
            emit(false)
            return@catch
        }.flowOn(Dispatchers.IO)
    }

}
