package com.sunil.app.data.remote.retrofit.datasource.movies

import androidx.paging.PagingSource
import com.sunil.app.data.exception.DataNotAvailableException
import com.sunil.app.data.local.db.dao.movies.FavoriteMovieDao
import com.sunil.app.data.local.db.entity.movies.FavoriteMovieDbData
import com.sunil.app.data.local.db.entity.movies.MovieDbData
import com.sunil.app.domain.model.Result

/**
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
class FavoriteMoviesLocalDataSource(
    private val favoriteMovieDao: FavoriteMovieDao,
) : FavoriteMoviesDataSource.Local {

    override fun favoriteMovies(): PagingSource<Int, MovieDbData> = favoriteMovieDao.getFavoriteMovies()

    override suspend fun addMovieToFavorite(movieId: Int) {
        favoriteMovieDao.addFavoriteMovie(FavoriteMovieDbData(movieId))
    }

    override suspend fun removeMovieFromFavorite(movieId: Int) {
        favoriteMovieDao.removeFavoriteMovieById(movieId)
    }

    override suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean> {
        return Result.Success(favoriteMovieDao.getFavoriteMovieById(movieId) != null)
    }

    override suspend fun getFavoriteMovieIds(): Result<List<Int>> {
        val movieIds = favoriteMovieDao.getAllFavoriteMovies().map { it.id }
        return if (movieIds.isNotEmpty()) {
            Result.Success(movieIds)
        } else {
            Result.Error(DataNotAvailableException())
        }
    }
}
