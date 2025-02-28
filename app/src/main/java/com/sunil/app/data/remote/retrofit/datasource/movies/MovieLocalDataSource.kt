package com.sunil.app.data.remote.retrofit.datasource.movies

import androidx.paging.PagingSource
import com.sunil.app.data.exception.DataNotAvailableException
import com.sunil.app.data.local.db.dao.movies.MovieDao
import com.sunil.app.data.local.db.dao.movies.MovieRemoteKeyDao
import com.sunil.app.data.local.db.entity.movies.MovieData
import com.sunil.app.data.local.db.entity.movies.MovieDbData
import com.sunil.app.data.local.db.entity.movies.MovieRemoteKeyDbData
import com.sunil.app.data.local.db.entity.movies.toDbData
import com.sunil.app.data.local.db.entity.movies.toDomain
import com.sunil.app.domain.entity.movies.MovieEntity
import com.sunil.app.domain.model.Result

/**
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
class MovieLocalDataSource(
    private val movieDao: MovieDao,
    private val remoteKeyDao: MovieRemoteKeyDao,
) : MovieDataSource.Local {

    override fun movies(): PagingSource<Int, MovieDbData> = movieDao.getPagedMovies()

    override suspend fun getMovies(): Result<List<MovieEntity>> {
        val movies = movieDao.getAllMovies()
        return if (movies.isNotEmpty()) {
            Result.Success(movies.map { it.toDomain() })
        } else {
            Result.Error(DataNotAvailableException())
        }
    }

    override suspend fun getMovie(movieId: Int): Result<MovieEntity> {
        return movieDao.getMovieById(movieId)?.let {
            Result.Success(it.toDomain())
        } ?: Result.Error(DataNotAvailableException())
    }

    override suspend fun saveMovies(movies: List<MovieData>) {
        movieDao.insertOrReplaceMovies(movies.map { it.toDbData() })
    }

    override suspend fun getLastRemoteKey(): MovieRemoteKeyDbData? {
        return remoteKeyDao.getLastRemoteKey()
    }

    override suspend fun saveRemoteKey(key: MovieRemoteKeyDbData) {
        remoteKeyDao.insertRemoteKey(key)
    }

    override suspend fun clearMovies() {
        movieDao.deleteNonFavoriteMovies()
    }

    override suspend fun clearRemoteKeys() {
        remoteKeyDao.clearRemoteKeys()
    }
}
