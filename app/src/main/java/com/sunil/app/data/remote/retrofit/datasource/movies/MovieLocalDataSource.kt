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
 * Local data source for movie-related operations.
 *
 * This class handles interactions with the local database for movies, including fetching,
 * saving, and clearing movie data and remote keys.
 *
 * @property movieDao The Data Access Object for movie entities.
 * @property remoteKeyDao The Data Access Object for movie remote keys.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
class MovieLocalDataSource(
    private val movieDao: MovieDao,
    private val remoteKeyDao: MovieRemoteKeyDao,
) : MovieDataSource.Local {

    /**
     * Retrieves a PagingSource for paginated movie data.
     *
     * @return A PagingSource that emits pages of MovieDbData.
     */
    override fun getPagedMovies(): PagingSource<Int, MovieDbData> = movieDao.getPagedMovies()

    /**
     * Retrieves all movies from the local database.
     *
     * @return A Result containing a list of MovieEntity if successful, or an error if no data is available.
     */
    override suspend fun getAllMovies(): Result<List<MovieEntity>> {
        val movies = movieDao.getAllMovies()
        return if (movies.isNotEmpty()) {
            Result.Success(movies.map { it.toDomain() })
        } else {
            Result.Error(DataNotAvailableException("No movies found in the local database."))
        }
    }

    /**
     * Retrieves a movie by its ID from the local database.
     *
     * @param movieId The ID of the movie to retrieve.
     * @return A Result containing the MovieEntity if found, or an error if not found.
     */
    override suspend fun getMovieById(movieId: Int): Result<MovieEntity> {
        return movieDao.getMovieById(movieId)?.let {
            Result.Success(it.toDomain())
        }
            ?: Result.Error(DataNotAvailableException("Movie with ID $movieId not found in the local database."))
    }

    /**
     * Saves a list of movies to the local database.
     *
     * @param movies The list of MovieData to save.
     */
    override suspend fun saveMovies(movies: List<MovieData>) {
        movieDao.insertOrReplaceMovies(movies.map { it.toDbData() })
    }

    /**
     * Retrieves the last remote key from the local database.
     *
     * @return The last MovieRemoteKeyDbData, or null if no remote keys are available.
     */
    override suspend fun getLastRemoteKey(): MovieRemoteKeyDbData? {
        return remoteKeyDao.getLastRemoteKey()
    }

    /**
     * Saves a remote key to the local database.
     *
     * @param key The MovieRemoteKeyDbData to save.
     */
    override suspend fun saveRemoteKey(key: MovieRemoteKeyDbData) {
        remoteKeyDao.insertRemoteKey(key)
    }

    /**
     * Clears non-favorite movies from the local database.
     */
    override suspend fun clearMovies() {
        movieDao.deleteNonFavoriteMovies()
    }

    /**
     * Clears all remote keys from the local database.
     */
    override suspend fun clearRemoteKeys() {
        remoteKeyDao.clearRemoteKeys()
    }
}
