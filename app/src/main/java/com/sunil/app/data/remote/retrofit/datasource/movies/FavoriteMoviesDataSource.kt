package com.sunil.app.data.remote.retrofit.datasource.movies

import androidx.paging.PagingSource
import com.sunil.app.data.local.db.entity.movies.MovieDbData
import com.sunil.app.domain.model.Result

/**
 * Interface defining data sources for favorite movies.
 *
 * This interface outlines the contract for interacting with local storage to manage
 * favorite movies, including fetching, adding, removing, and checking their status.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
interface FavoriteMoviesDataSource {

    /**
     * Interface defining local data source operations for favorite movies.
     */
    interface Local {
        /**
         * Provides a [PagingSource] for fetching favorite movies in a paginated manner.
         *
         * @return A [PagingSource] that emits pages of [MovieDbData].
         */
        fun favoriteMovies(): PagingSource<Int, MovieDbData>

        /**
         * Retrieves a list of IDs for all movies marked as favorites.
         *
         * @return A [Result] containing a list of favorite movie IDs or an error.
         */
        suspend fun getFavoriteMovieIds(): Result<List<Int>>

        /**
         * Adds a movie to the list of favorites.
         *
         * @param movieId The ID of the movie to add.
         */
        suspend fun addMovieToFavorite(movieId: Int)

        /**
         * Removes a movie from the list of favorites.
         *
         * @param movieId The ID of the movie to remove.
         */
        suspend fun removeMovieFromFavorite(movieId: Int)

        /**
         * Checks if a movie is currently marked as a favorite.
         *
         * @param movieId The ID of the movie to check.
         * @return A [Result] containing `true` if the movie is a favorite, `false` otherwise, or an error.
         */
        suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean>
    }
}
