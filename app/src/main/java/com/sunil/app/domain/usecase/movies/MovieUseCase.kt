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

@Singleton
class AddMovieToFavoritesUseCase @Inject constructor(
    private val moviesDataManager: MoviesDataManager
) {
    suspend operator fun invoke(movieId: Int) = moviesDataManager.addMovieToFavorite(movieId)
}

@Singleton
class CheckFavoriteStatusUseCase @Inject constructor(
    private val moviesDataManager: MoviesDataManager
) {
    suspend operator fun invoke(movieId: Int): Result<Boolean> =
        moviesDataManager.checkFavoriteStatus(movieId)
}


@Singleton
class GetFavoriteMoviesUseCase @Inject constructor(
    private val moviesDataManager: MoviesDataManager
) {
    operator fun invoke(pageSize: Int): Flow<PagingData<MovieEntity>> =
        moviesDataManager.favoriteMovies(pageSize)
}


@Singleton
class GetMovieDetailsUseCase @Inject constructor(
    private val moviesDataManager: MoviesDataManager
) {
    suspend operator fun invoke(movieId: Int): Result<MovieEntity> =
        moviesDataManager.getMovie(movieId)
}

@Singleton
class RemoveMovieFromFavoriteUseCase @Inject constructor(
    private val moviesDataManager: MoviesDataManager
) {
    suspend operator fun invoke(movieId: Int) = moviesDataManager.removeMovieFromFavorite(movieId)
}

@Singleton
class SearchMoviesUseCase @Inject constructor(
    private val moviesDataManager: MoviesDataManager
) {
    operator fun invoke(query: String, pageSize: Int): Flow<PagingData<MovieEntity>> =
        moviesDataManager.search(query, pageSize)
}

@Singleton
class GetMoviesWithSeparatorsUseCase @Inject constructor(
    private val moviesDataManager: MoviesDataManager,
    private val insertSeparatorIntoPagingData: InsertSeparatorIntoPagingData
) {

    fun movies(pageSize: Int): Flow<PagingData<MovieListItem>> =
        moviesDataManager.movies(pageSize).map {
            val pagingData: PagingData<MovieListItem.Movie> =
                it.map { movie -> movie.toPresentation() }
            insertSeparatorIntoPagingData.insert(pagingData)
        }
}
