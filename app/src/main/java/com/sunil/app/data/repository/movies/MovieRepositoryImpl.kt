package com.sunil.app.data.repository.movies


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sunil.app.data.local.db.entity.movies.toDomain
import com.sunil.app.data.remote.retrofit.datasource.movies.FavoriteMoviesDataSource
import com.sunil.app.data.remote.retrofit.datasource.movies.MovieDataSource
import com.sunil.app.data.remote.retrofit.datasource.movies.MovieRemoteMediator
import com.sunil.app.data.remote.retrofit.datasource.movies.SearchMoviePagingSource
import com.sunil.app.domain.entity.movies.MovieEntity
import com.sunil.app.domain.model.map
import com.sunil.app.domain.model.onError
import com.sunil.app.domain.model.onSuccess
import com.sunil.app.domain.model.Result
import com.sunil.app.domain.repository.movies.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

/**
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */

class MovieRepositoryImpl @Inject constructor(
    private val remote: MovieDataSource.Remote,
    private val local: MovieDataSource.Local,
    private val remoteMediator: MovieRemoteMediator,
    private val localFavorite: FavoriteMoviesDataSource.Local
) : MovieRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun movies(pageSize: Int): Flow<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        remoteMediator = remoteMediator,
        pagingSourceFactory = { local.movies() }
    ).flow.map { pagingData ->
        pagingData.map { it.toDomain() }
    }

    override fun favoriteMovies(pageSize: Int): Flow<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { localFavorite.favoriteMovies() }
    ).flow.map { pagingData ->
        pagingData.map { it.toDomain() }
    }

    override fun search(query: String, pageSize: Int): Flow<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { SearchMoviePagingSource(query, remote) }
    ).flow.map { pagingData ->
        pagingData.map { it.toDomain() }
    }

    override suspend fun getMovie(id: Int): Result<MovieEntity> {
        return when (val localResult = local.getMovie(id)) {
            is Result.Success -> localResult
            is Result.Error -> remote.getMovie(id).map { it.toDomain() }
        }
    }

    override suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean> =
        localFavorite.checkFavoriteStatus(movieId)

    override suspend fun addMovieToFavorite(movieId: Int) {
        local.getMovie(movieId)
            .onSuccess {
                localFavorite.addMovieToFavorite(movieId)
            }
            .onError {
                remote.getMovie(movieId).onSuccess { movie ->
                    local.saveMovies(listOf(movie))
                    localFavorite.addMovieToFavorite(movieId)
                }
            }
    }

    override suspend fun removeMovieFromFavorite(movieId: Int) =
        localFavorite.removeMovieFromFavorite(movieId)

    override suspend fun sync(): Boolean {
        return when (val result = local.getMovies()) {
            is Result.Error -> false
            is Result.Success -> {
                val movieIds = result.data.map { it.id }
                return updateLocalWithRemoteMovies(movieIds)
            }
        }
    }

    private suspend fun updateLocalWithRemoteMovies(movieIds: List<Int>): Boolean {
        return when (val remoteResult = remote.getMovies(movieIds)) {
            is Result.Error -> false
            is Result.Success -> {
                local.saveMovies(remoteResult.data)
                true
            }
        }
    }
}
