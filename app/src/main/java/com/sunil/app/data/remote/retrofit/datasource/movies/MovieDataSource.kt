package com.sunil.app.data.remote.retrofit.datasource.movies

import androidx.paging.PagingSource
import com.sunil.app.data.local.db.entity.movies.MovieData
import com.sunil.app.data.local.db.entity.movies.MovieDbData
import com.sunil.app.data.local.db.entity.movies.MovieRemoteKeyDbData
import com.sunil.app.domain.entity.movies.MovieEntity
import com.sunil.app.domain.model.Result


/**
 * Data sources for movie-related operations.
 *
 * This interface defines the contract for fetching and managing movie data,
 * both from remote sources (e.g., network) and local sources (e.g., database).
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
interface MovieDataSource {

    /**
     * Interface for remote movie data operations.
     */
    interface Remote {
        /**
         * Retrieves a list of movies from a remote source.
         *
         * @param page The page number to retrieve.
         * @param limit The maximum number of movies to retrieve per page.
         * @return A [Result] containing a list of [MovieData] or an error.
         */
        suspend fun getMovies(page: Int, limit: Int): Result<List<MovieData>>

        /**
         * Retrieves a list of movies from a remote source based on their IDs.
         *
         * @param movieIds The list of movie IDs to retrieve.
         * @return A [Result] containing a list of [MovieData] or an error.
         */
        suspend fun getMoviesByIds(movieIds: List<Int>): Result<List<MovieData>>

        /**
         * Retrieves a single movie from a remote source by its ID.
         *
         * @param movieId The ID of the movie to retrieve.
         * @return A [Result] containing a [MovieData] or an error.
         */
        suspend fun getMovieById(movieId: Int): Result<MovieData>

        /**
         * Searches for movies from a remote source based on a query.
         *
         * @param query The search query string.
         * @param page The page number to retrieve.
         * @param limit The maximum number of movies to retrieve per page.
         * @return A [Result] containing a list of [MovieData] or an error.
         */
        suspend fun searchMovies(query: String, page: Int, limit: Int): Result<List<MovieData>>
    }

    /**
     * Interface for local movie data operations.
     */
    interface Local {
        /**
         * Provides a [PagingSource] for movies from the local database.
         *
         * @return A [PagingSource] that emits pages of [MovieDbData].
         */
        fun getPagedMovies(): PagingSource<Int, MovieDbData>

        /**
         * Retrieves all movies from the local database.
         *
         * @return A [Result] containing a list of [MovieEntity] or an error.
         */
        suspend fun getAllMovies(): Result<List<MovieEntity>>

        /**
         * Retrieves a single movie from the local database by its ID.
         *
         * @param movieId The ID of the movie to retrieve.
         * @return A [Result] containing a [MovieEntity] or an error.
         */
        suspend fun getMovieById(movieId: Int): Result<MovieEntity>

        /**
         * Saves a list of movies to the local database.
         *
         * @param movies The list of [MovieData] to save.
         */
        suspend fun saveMovies(movies: List<MovieData>)

        /**
         * Retrieves the last remote key from the local database.
         *
         * @return The last [MovieRemoteKeyDbData] or null if none exists.
         */
        suspend fun getLastRemoteKey(): MovieRemoteKeyDbData?

        /**
         * Saves a remote key to the local database.
         *
         * @param key The [MovieRemoteKeyDbData] to save.
         */
        suspend fun saveRemoteKey(key: MovieRemoteKeyDbData)

        /**
         * Clears all movie data from the local database.
         */
        suspend fun clearMovies()

        /**
         * Clears all remote keys from the local database.
         */
        suspend fun clearRemoteKeys()
    }
}
