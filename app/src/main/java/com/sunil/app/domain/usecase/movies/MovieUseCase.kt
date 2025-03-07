package com.sunil.app.domain.usecase.movies

import androidx.paging.PagingData
import androidx.paging.map
import com.sunil.app.domain.entity.movies.MovieEntity
import com.sunil.app.domain.manager.MoviesDataManager
import com.sunil.app.domain.model.Result
import com.sunil.app.presentation.entity.movies.MovieListItem
import com.sunil.app.presentation.mapper.movies.toPresentation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use case for adding a movie to favorites.
 */
@Singleton
class AddMovieToFavoritesUseCase @Inject constructor(
    private val moviesDataManager: MoviesDataManager
) {
    /**
     * Adds a movie to the user's favorites.
     *
     * @param movieId The ID of the movie to add.
     */suspend operator fun invoke(movieId: Int) = moviesDataManager.addMovieToFavorites(movieId)
}

/**
 * Use case for checking if a movie is in favorites.
 */
@Singleton
class CheckFavoriteStatusUseCase @Inject constructor(
    private val moviesDataManager: MoviesDataManager
) {
    /**
     * Checks if a movie is in the user's favorites.
     *
     * @param movieId The ID of the movie to check.
     * @return A [Result] indicating whether the movie is a favorite.
     */
    suspend operator fun invoke(movieId: Int): Result<Boolean> =
        moviesDataManager.isMovieFavorite(movieId)
}

/**
 * Use case for retrieving the user's favorite movies.
 */
@Singleton
class GetFavoriteMoviesUseCase @Inject constructor(
    private val moviesDataManager: MoviesDataManager
) {
    /**
     * Retrieves the user's favorite movies.
     *
     * @param pageSize The number of movies to retrieve per page.
     * @return A [Flow] of [PagingData] containing the favorite movies.
     */
    operator fun invoke(pageSize: Int): Flow<PagingData<MovieEntity>> =
        moviesDataManager.getFavoriteMovies(pageSize)
}

/**
 * Use case for retrieving movie details.
 */
@Singleton
class GetMovieDetailsUseCase @Inject constructor(
    private val moviesDataManager: MoviesDataManager
) {
    /**
     * Retrieves the details of a specific movie.
     *
     * @param movieId The ID of the movie to retrieve.
     * @return A [Result] containing the movie details.
     */
    suspend operator fun invoke(movieId: Int): Result<MovieEntity> =
        moviesDataManager.getMovie(movieId)

//    suspend operator fun invoke(movieId: Int): Result<MovieEntity> {
//        if (movieId <= 0) {
//            return Result.Error(IllegalArgumentException("Invalid movieId: Must be greater than 0"))
//        }
//        return moviesDataManager.getMovie(movieId)
//    }
}

/**
 * Use case for removing a movie from favorites.
 */
@Singleton
class RemoveMovieFromFavoriteUseCase @Inject constructor(
    private val moviesDataManager: MoviesDataManager
) {
    /**
     * Removes a movie from the user's favorites.
     *
     * @param movieId The ID of the movie to remove.
     */
    suspend operator fun invoke(movieId: Int) = moviesDataManager.removeMovieFromFavorites(movieId)
}

/**
 * Use case for searching movies.
 */
@Singleton
class SearchMoviesUseCase @Inject constructor(
    private val moviesDataManager: MoviesDataManager
) {
    /**
     * Searches for movies based on a query.
     *
     * @param query The search query.
     * @param pageSize The number of movies to retrieve per page.
     * @return A [Flow] of [PagingData] containing the search results.
     */
    operator fun invoke(query: String, pageSize: Int): Flow<PagingData<MovieEntity>> =
        moviesDataManager.searchMovies(query, pageSize)
}

/**
 * Use case for retrieving movies with separators.
 */
@Singleton
class GetMoviesWithSeparatorsUseCase @Inject constructor(
    private val moviesDataManager: MoviesDataManager,
    private val insertSeparatorIntoPagingData: InsertSeparatorIntoPagingData
) {
    /**
     * Retrieves movies with separators inserted into the list.
     *
     * @param pageSize The number of movies to retrieve per page.
     * @return A [Flow] of [PagingData] containing the movies with separators.
     */
    fun invoke(pageSize: Int): Flow<PagingData<MovieListItem>> =
        moviesDataManager.getMovies(pageSize).map { moviePagingData ->
            moviePagingData.map { movieEntity -> movieEntity.toPresentation() }
                .let { movieListItemPagingData ->
                    insertSeparatorIntoPagingData.insert(movieListItemPagingData)
                }
        }
}
