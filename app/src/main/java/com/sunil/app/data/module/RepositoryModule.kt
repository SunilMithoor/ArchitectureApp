package com.sunil.app.data.module


import com.sunil.app.data.remote.retrofit.datasource.movies.FavoriteMoviesDataSource
import com.sunil.app.data.remote.retrofit.datasource.movies.MovieDataSource
import com.sunil.app.data.remote.retrofit.datasource.movies.MovieRemoteMediator
import com.sunil.app.data.repository.restful.RestfulRepositoryImpl
import com.sunil.app.data.repository.movies.MovieRepositoryImpl
import com.sunil.app.data.repository.onboarding.OnBoardingRepositoryImpl
import com.sunil.app.domain.repository.restful.RestfulRepository
import com.sunil.app.domain.repository.movies.MovieRepository
import com.sunil.app.domain.repository.onboarding.OnBoardingRepository
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

//    @Provides
//    @Singleton
//    fun providerMovieRepository(repository: MovieRepositoryImpl): MovieRepository {
//        return repository
//    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieRemote: MovieDataSource.Remote,
        movieLocal: MovieDataSource.Local,
        movieRemoteMediator: MovieRemoteMediator,
        favoriteLocal: FavoriteMoviesDataSource.Local,
    ): MovieRepository {
        return MovieRepositoryImpl(movieRemote, movieLocal, movieRemoteMediator, favoriteLocal)
    }

    @Provides
    @Singleton
    fun providerOnBoardingRepository(repository: OnBoardingRepositoryImpl): OnBoardingRepository {
        return repository
    }

}
