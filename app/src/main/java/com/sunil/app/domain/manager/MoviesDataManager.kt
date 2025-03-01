package com.sunil.app.domain.manager


import androidx.paging.PagingData
import com.sunil.app.domain.entity.movies.MovieEntity
import com.sunil.app.domain.repository.movies.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.sunil.app.domain.model.Result


/**
 * [MoviesDataManager] isresponsible for managing movie-related data operations.
 * It acts as an intermediary between the presentation layer and the [MovieRepository],
 * providing a clean interface for accessing and manipulating movie data.
 */
class MoviesDataManager @Inject constructor(
    private val movieRepository: MovieRepository,
) : MovieRepository {

    /**
     * Retrieves a paginated flow of movies.
     *
     * @param pageSize The number of movies to include in each page.
     * @return A [Flow] emitting [PagingData] of [MovieEntity].
     */override fun getMovies(pageSize: Int): Flow<PagingData<MovieEntity>> =
        movieRepository.getMovies(pageSize)

    /**
     * Retrieves a paginated flow of favorite movies.
     *
     * @param pageSize The number of movies to include in each page.
     * @return A [Flow] emitting [PagingData] of [MovieEntity].
     */
    override fun getFavoriteMovies(pageSize: Int): Flow<PagingData<MovieEntity>> =
        movieRepository.getFavoriteMovies(pageSize)

    /**
     * Searches for movies based on a query.
     *
     * @param query The search query string.
     * @param pageSize The number of movies to include in each page.
     * @return A [Flow] emitting [PagingData] of [MovieEntity] matching the query.
     */
    override fun searchMovies(query: String, pageSize: Int): Flow<PagingData<MovieEntity>> =
        movieRepository.searchMovies(query, pageSize)

    /**
     * Retrieves a specific movie by its ID.
     *
     * @param movieId The ID of themovie to retrieve.
     * @return A [Result] containing the [MovieEntity] or an error.
     */
    override suspend fun getMovie(movieId: Int): Result<MovieEntity> =
        movieRepository.getMovie(movieId)

    /**
     * Checks if a movie is marked as a favorite.
     *
     * @param movieId The ID of the movie to check.
     * @return A [Result] containing a [Boolean] indicating if the movie is a favorite.
     */
    override suspend fun isMovieFavorite(movieId: Int): Result<Boolean> =
        movieRepository.isMovieFavorite(movieId)

    /**
     * Adds a movie to the user's favorites.
     *
     * @param movieId The ID of the movie to add.
     */
    override suspend fun addMovieToFavorites(movieId: Int) {
        movieRepository.addMovieToFavorites(movieId)
    }

    /**
     * Removes a movie from the user's favorites.
     *
     * @param movieId The ID of the movie to remove.
     */
    override suspend fun removeMovieFromFavorites(movieId: Int) {
        movieRepository.removeMovieFromFavorites(movieId)
    }

    /**
     * Synchronizes the local movie data with a remote source.
     *
     * @return `true` if the synchronization was successful, `false` otherwise.
     */
    override suspend fun syncMovies(): Boolean =
        movieRepository.syncMovies()
}
