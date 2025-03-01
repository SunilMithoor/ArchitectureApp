package com.sunil.app.data.remote.retrofit.datasource.movies

import com.sunil.app.data.local.db.entity.movies.MovieData
import com.sunil.app.data.remote.retrofit.api.ApiAppBaseUrl3Service
import com.sunil.app.data.utils.safeApiCall
import com.sunil.app.domain.model.Result
import javax.inject.Inject

/**
 * Remote data source for movie-related operations.
 *
 * This class handles fetching movie data from a remote API.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-02-16
 */
class MovieRemoteDataSource @Inject constructor( // Added @Inject for dependency injection
    private val apiAppBaseUrl3Service: ApiAppBaseUrl3Service
) : MovieDataSource.Remote {

    /**
     * Retrieves a list of movies based on pagination.
     *
     * @param page The page number to retrieve.
     * @param limit The number of movies per page.
     * @return A [Result]containing a list of [MovieData] or an error.
     */
    override suspend fun getMovies(page: Int, limit: Int): Result<List<MovieData>> = safeApiCall {
        apiAppBaseUrl3Service.getMoviesSortedByCategoryAndId(page, limit)
    }

    /**
     * Retrieves a list of movies based on their IDs.
     *
     * @param movieIds The list of movie IDs to retrieve.
     * @return A [Result] containing a list of [MovieData] or an error.
     */
    override suspend fun getMoviesByIds(movieIds: List<Int>): Result<List<MovieData>> =
        safeApiCall {
            apiAppBaseUrl3Service.getMoviesByIds(movieIds)
        }

    /**
     * Retrieves a single movie by its ID.
     *
     * @param movieId The ID of the movie to retrieve.
     * @return A [Result] containing a [MovieData] or an error.
     */
    override suspend fun getMovieById(movieId: Int): Result<MovieData> = safeApiCall {
        apiAppBaseUrl3Service.getMovieById(movieId)
    }

    /**
     * Searches for movies based on a query.
     *
     * @param query The search query.
     * @param page The page number to retrieve.
     * @param limit The number of movies per page.
     * @return A [Result] containing a list of [MovieData] or an error.
     */
    override suspend fun searchMovies(
        query: String,
        page: Int,
        limit: Int
    ): Result<List<MovieData>> = safeApiCall {
        apiAppBaseUrl3Service.searchMovies(query, page, limit)
    }
}
