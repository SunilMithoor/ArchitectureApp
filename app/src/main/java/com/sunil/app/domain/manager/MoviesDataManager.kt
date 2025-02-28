package com.sunil.app.domain.manager


import androidx.paging.PagingData
import com.sunil.app.domain.entity.movies.MovieEntity
import com.sunil.app.domain.repository.movies.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.sunil.app.domain.model.Result


class MoviesDataManager @Inject constructor(
    private val movieRepository: MovieRepository,
) : MovieRepository {

    override fun movies(pageSize: Int): Flow<PagingData<MovieEntity>> =
        movieRepository.movies(pageSize)

    override fun favoriteMovies(pageSize: Int): Flow<PagingData<MovieEntity>> =
        movieRepository.favoriteMovies(pageSize)

    override fun search(query: String, pageSize: Int): Flow<PagingData<MovieEntity>> =
        movieRepository.search(query,pageSize)

    override suspend fun getMovie(movieId: Int): Result<MovieEntity> =
        movieRepository.getMovie(movieId)

    override suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean> =
        movieRepository.checkFavoriteStatus(movieId)

    override suspend fun addMovieToFavorite(movieId: Int) =
        movieRepository.addMovieToFavorite(movieId)

    override suspend fun removeMovieFromFavorite(movieId: Int) =
        movieRepository.removeMovieFromFavorite(movieId)

    override suspend fun sync(): Boolean =
        movieRepository.sync()

}
