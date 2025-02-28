package com.sunil.app.domain.repository.movies

import androidx.paging.PagingData
import com.sunil.app.domain.entity.movies.MovieEntity
import kotlinx.coroutines.flow.Flow
import com.sunil.app.domain.model.Result

/**
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
interface MovieRepository {
    fun movies(pageSize: Int): Flow<PagingData<MovieEntity>>
    fun favoriteMovies(pageSize: Int): Flow<PagingData<MovieEntity>>
    fun search(query: String, pageSize: Int): Flow<PagingData<MovieEntity>>
    suspend fun getMovie(movieId: Int): Result<MovieEntity>
    suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean>
    suspend fun addMovieToFavorite(movieId: Int)
    suspend fun removeMovieFromFavorite(movieId: Int)
    suspend fun sync(): Boolean
}
