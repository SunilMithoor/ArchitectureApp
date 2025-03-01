package com.sunil.app.data.remote.retrofit.api

import com.sunil.app.data.local.db.entity.movies.MovieData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Interface defining the API endpoints for interacting with movie data.
 *
 * @author Sunil
 * @since 1.0
 * @since 2025-01-28
 */
interface ApiAppBaseUrl3Service {

    /**
     * Retrieves a paginated list of movies, sorted by category and then by ID.
     *
     * @param page The page number to retrieve.
     * @param limit The maximum number of movies to retrieve per page.
     * @return A list of MovieData objects.
     */
    @GET("/movies?_sort=category,id")
    suspend fun getMoviesSortedByCategoryAndId(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int,
    ): List<MovieData>

    /**
     * Retrieves a list of movies based on their IDs.
     *
     * @param movieIds A list of movie IDs to retrieve.
     * @return A list of MovieData objects.
     */
    @GET("/movies")
    suspend fun getMoviesByIds(@Query("id") movieIds: List<Int>): List<MovieData>

    /**
     * Retrieves a single movie by its ID.
     *
     * @param movieId The ID of the movie to retrieve.
     * @return The MovieData object corresponding to the given ID.
     */
    @GET("/movies/{id}")
    suspend fun getMovieById(@Path("id") movieId: Int): MovieData

    /**
     * Searches for movies based on a query string, with pagination.
     *
     * @param query The search query string.
     * @param page The page number to retrieve.
     * @param limit The maximum number of movies to retrieve per page.
     * @return A list of MovieData objects matching the search query.
     */
    @GET("/movies")
    suspend fun searchMovies(
        @Query("q") query: String,
        @Query("_page") page: Int,
        @Query("_limit") limit: Int,
    ): List<MovieData>
}
