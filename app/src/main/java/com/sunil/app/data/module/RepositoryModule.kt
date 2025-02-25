package com.sunil.app.data.module


import com.sunil.app.data.repository.RestfulRepositoryImpl
import com.sunil.app.domain.repository.RestfulRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providerRestfulRepository(repository: RestfulRepositoryImpl): RestfulRepository {
        return repository
    }

}
