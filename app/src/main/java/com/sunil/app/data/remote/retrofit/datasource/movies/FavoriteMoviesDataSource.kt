package com.sunil.app.data.remote.retrofit.datasource.movies

import androidx.paging.PagingSource
import com.sunil.app.data.local.db.entity.movies.MovieDbData
import com.sunil.app.domain.model.Result

/**
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
interface FavoriteMoviesDataSource {

    interface Local {
        fun favoriteMovies(): PagingSource<Int, MovieDbData>
        suspend fun getFavoriteMovieIds(): Result<List<Int>>
        suspend fun addMovieToFavorite(movieId: Int)
        suspend fun removeMovieFromFavorite(movieId: Int)
        suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean>
    }
}
