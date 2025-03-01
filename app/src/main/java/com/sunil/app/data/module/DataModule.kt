package com.sunil.app.data.module

import com.sunil.app.data.local.db.dao.movies.FavoriteMovieDao
import com.sunil.app.data.local.db.dao.movies.MovieDao
import com.sunil.app.data.local.db.dao.movies.MovieRemoteKeyDao
import com.sunil.app.data.remote.retrofit.api.ApiAppBaseUrl3Service
import com.sunil.app.data.remote.retrofit.datasource.movies.FavoriteMoviesDataSource
import com.sunil.app.data.remote.retrofit.datasource.movies.FavoriteMoviesLocalDataSource
import com.sunil.app.data.remote.retrofit.datasource.movies.MovieDataSource
import com.sunil.app.data.remote.retrofit.datasource.movies.MovieLocalDataSource
import com.sunil.app.data.remote.retrofit.datasource.movies.MovieRemoteDataSource
import com.sunil.app.data.remote.retrofit.datasource.movies.MovieRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * DataModule provides dependencies related to data sources and mediators.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    /**
     * Provides the remote data source for movies.
     *
     * @param api AppBaseUrl3Service The service for making API calls.
     * @return An instance of MovieRemoteDataSource.
     */
    @Provides
    @Singleton
    fun provideMovieRemoveDataSource(apiAppBaseUrl3Service: ApiAppBaseUrl3Service): MovieDataSource.Remote {
        return MovieRemoteDataSource(apiAppBaseUrl3Service)
    }

    /**
     * Provides the movie local data source
     *
     * @param movieDao The dao source for movies
     * @param movieRemoteKeyDao The remote dao for movies.
     * @return An instance of MovieLocalDataSource.
     */
    @Provides
    @Singleton
    fun provideMovieLocalDataSource(
        movieDao: MovieDao,
        movieRemoteKeyDao: MovieRemoteKeyDao,
    ): MovieDataSource.Local {
        return MovieLocalDataSource(movieDao, movieRemoteKeyDao)
    }

    /**
     * Provides the movie remote mediator.
     *
     * @param movieLocalDataSource The local data source for movies.
     * @param movieRemoteDataSource The remote data source for movies.
     * @return An instance of MovieRemoteMediator.
     */
    @Provides
    @Singleton
    fun provideMovieMediator(
        movieLocalDataSource: MovieDataSource.Local,
        movieRemoteDataSource: MovieDataSource.Remote
    ): MovieRemoteMediator {
        return MovieRemoteMediator(movieLocalDataSource, movieRemoteDataSource)
    }

    /**
     * Provides the favorite local data source
     *
     * @param favoriteMovieDao The dao source for favorite movies
     * @return An instance of FavoriteMoviesLocalDataSource.
     */
    @Provides
    @Singleton
    fun provideFavoriteMovieLocalDataSource(
        favoriteMovieDao: FavoriteMovieDao
    ): FavoriteMoviesDataSource.Local {
        return FavoriteMoviesLocalDataSource(favoriteMovieDao)
    }

}
