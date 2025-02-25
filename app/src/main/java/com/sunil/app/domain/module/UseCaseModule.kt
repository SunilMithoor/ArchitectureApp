package com.sunil.app.domain.module


import com.sunil.app.domain.manager.RestfulDataManager
import com.sunil.app.domain.usecase.GetAllDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
object UseCaseModule {


    @Provides
    fun provideGetAllDataCase(restfulDataManager: RestfulDataManager): GetAllDataUseCase =
        GetAllDataUseCase(
            restfulDataManager
        )
}
