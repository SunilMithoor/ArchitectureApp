package com.sunil.app.data.remote.retrofit.datasource.movies

import androidx.paging.PagingSource
import com.sunil.app.data.exception.DataNotAvailableException
import com.sunil.app.data.local.db.dao.movies.FavoriteMovieDao
import com.sunil.app.data.local.db.entity.movies.FavoriteMovieDbData
import com.sunil.app.data.local.db.entity.movies.MovieDbData
import com.sunil.app.domain.model.Result



/**
 * FavoriteMoviesLocalDataSource: Local data source for managing favorite movies.
 *
 * This class implements the [FavoriteMoviesDataSource.Local] interface and provides
 * methods for interacting with the local database to manage favorite movies.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
class FavoriteMoviesLocalDataSource(
    private val favoriteMovieDao: FavoriteMovieDao,
) : FavoriteMoviesDataSource.Local {

    /**
     * Provides a [PagingSource] for fetching favorite movies in a paginated manner.
     *
     * @return A [PagingSource] that emits pages of [MovieDbData].
     */
    override fun favoriteMovies(): PagingSource<Int, MovieDbData> =
        favoriteMovieDao.getFavoriteMovies()

    /**
     * Adds a movie to the list of favorites.
     *
     * @param movieId The ID of the movie to add.
     */
    override suspend fun addMovieToFavorite(movieId: Int) {
        val favoriteMovie = FavoriteMovieDbData(movieId)
        favoriteMovieDao.addFavoriteMovie(favoriteMovie)
    }

    /**
     * Removes a movie from the list of favorites.
     *
     * @param movieId The ID of the movie to remove.
     */
    override suspend fun removeMovieFromFavorite(movieId: Int) {
        favoriteMovieDao.removeFavoriteMovieById(movieId)
    }

    /**
     * Checks if a movie is currently marked as a favorite.
     *
     * @param movieId The ID of themovie to check.
     * @return A [Result] containing `true` if the movie is a favorite, `false` otherwise.
     */
    override suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean> {
        val isFavorite = favoriteMovieDao.getFavoriteMovieById(movieId) != null
        return Result.Success(isFavorite)
    }

    /**
     * Retrieves a list of IDs for all movies marked as favorites.
     *
     * @return A [Result] containing a list of favorite movie IDs or an error if no favorites are found.
     */
    override suspend fun getFavoriteMovieIds(): Result<List<Int>> {
        val movieIds = favoriteMovieDao.getAllFavoriteMovies().map { it.id }
        return movieIds.takeIf { it.isNotEmpty() }?.let {
            Result.Success(it)
        } ?: Result.Error(DataNotAvailableException("Movie with ID $movieIds not found in the local database."))
    }
}
