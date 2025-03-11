package com.sunil.app.domain.repository.movies

import androidx.paging.PagingData
import com.sunil.app.domain.entity.movies.MovieEntity
import kotlinx.coroutines.flow.Flow
import com.sunil.app.domain.model.Result

/**
 * Interface defining the contract for movie-related data operations.
 *
 * This repository provides methods to fetch, search, and manage movie data,
 * including support for pagination and favorite movie management.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
interface MovieRepository {

    /**
     * Retrieves a paginated list of movies.
     *
     * @param pageSize The number of movies to include in each page.
     * @return A Flow emitting PagingData containing MovieEntity objects.
     */
    fun getMovies(pageSize: Int): Flow<PagingData<MovieEntity>>?

    /**
     * Retrieves a paginated list of the user's favorite movies.
     *
     * @param pageSize The number of movies to include in each page.
     * @return A Flow emitting PagingData containing MovieEntity objects.
     */
    fun getFavoriteMovies(pageSize: Int): Flow<PagingData<MovieEntity>>?

    /**
     * Searches for movies based on a query string.
     *
     * @param query The search query string.
     * @param pageSize The number of movies to include in each page.
     * @return A Flow emitting PagingData containing MovieEntity objects.
     */
    fun searchMovies(query: String, pageSize: Int): Flow<PagingData<MovieEntity>>?

    /**
     * Retrieves a single movie by its ID.
     *
     * @param movieId The ID of the movie to retrieve.
     * @return A Result object containing either the MovieEntity or an error.
     */
    suspend fun getMovie(movieId: Int): Result<MovieEntity>

    /**
     * Checks if a movie is marked as a favorite.
     *
     * @param movieId The ID of the movie to check.
     * @return A Result object containing either true/false or an error.
     */
    suspend fun isMovieFavorite(movieId: Int): Result<Boolean>

    /**
     * Adds a movie to the user's favorites.
     *
     * @param movieId The ID of the movie to add.
     */
    suspend fun addMovieToFavorites(movieId: Int)

    /**
     * Removes a movie from the user's favorites.
     *
     * @param movieId The ID of the movie to remove.
     */
    suspend fun removeMovieFromFavorites(movieId: Int)

    /**
     * Synchronizes the local movie data with a remote source.
     *
     * @return True if the synchronization was successful, false otherwise.
     */
    suspend fun syncMovies(): Boolean
}
