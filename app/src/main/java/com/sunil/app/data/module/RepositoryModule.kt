package com.sunil.app.data.module


import com.sunil.app.data.remote.retrofit.datasource.movies.FavoriteMoviesDataSource
import com.sunil.app.data.remote.retrofit.datasource.movies.MovieDataSource
import com.sunil.app.data.remote.retrofit.datasource.movies.MovieRemoteMediator
import com.sunil.app.data.repository.movies.MovieRepositoryImpl
import com.sunil.app.data.repository.onboarding.OnBoardingRepositoryImpl
import com.sunil.app.data.repository.restful.RestfulRepositoryImpl
import com.sunil.app.domain.repository.movies.MovieRepository
import com.sunil.app.domain.repository.onboarding.OnBoardingRepository
import com.sunil.app.domain.repository.restful.RestfulRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * RepositoryModule: Provides repository dependencies for the application.
 *
 * This module configures and provides instances of various repositories, such as
 * RestfulRepository, MovieRepository, and OnBoardingRepository, for dependency injection.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Binds the RestfulRepository interface to its implementation, RestfulRepositoryImpl.
     *
     * @param repository The RestfulRepositoryImpl instance to bind.
     * @return An instance of RestfulRepository.
     */
    @Binds
    @Singleton
    abstract fun bindRestfulRepository(repository: RestfulRepositoryImpl): RestfulRepository

    /**
     * Binds the OnBoardingRepository interface to its implementation, OnBoardingRepositoryImpl.
     *
     * @param repository The OnBoardingRepositoryImpl instance to bind.
     * @return An instance of OnBoardingRepository.
     */
    @Binds
    @Singleton
    abstract fun bindOnBoardingRepository(repository: OnBoardingRepositoryImpl): OnBoardingRepository

    /**
     * Companion object to provide dependencies that require parameters.
     *
     * This is necessary because @Provides methods that have parameters cannot be in an abstract class.
     */
    companion object {
        /**
         * Provides the MovieRepository instance.
         *
         * @param movieRemote The remote data source for movies.
         * @param movieLocal The local data source for movies.
         * @param movieRemoteMediator The remote mediator for movies.
         * @param favoriteLocal The local data source for favorite movies.
         * @return An instance of MovieRepository.
         */
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
    }
}

//@Module
//@InstallIn(SingletonComponent::class)
//object RepositoryModule {
//
//    @Provides
//    @Singleton
//    fun providerRestfulRepository(repository: RestfulRepositoryImpl): RestfulRepository {
//        return repository
//    }
//
////    @Provides
////    @Singleton
////    fun providerMovieRepository(repository: MovieRepositoryImpl): MovieRepository {
////        return repository
////    }
//
//    @Provides
//    @Singleton
//    fun provideMovieRepository(
//        movieRemote: MovieDataSource.Remote,
//        movieLocal: MovieDataSource.Local,
//        movieRemoteMediator: MovieRemoteMediator,
//        favoriteLocal: FavoriteMoviesDataSource.Local,
//    ): MovieRepository {
//        return MovieRepositoryImpl(movieRemote, movieLocal, movieRemoteMediator, favoriteLocal)
//    }
//
//    @Provides
//    @Singleton
//    fun providerOnBoardingRepository(repository: OnBoardingRepositoryImpl): OnBoardingRepository {
//        return repository
//    }
//
//}
