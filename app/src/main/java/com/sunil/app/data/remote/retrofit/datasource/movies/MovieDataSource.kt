package com.sunil.app.data.remote.retrofit.datasource.movies

import androidx.paging.PagingSource
import com.sunil.app.data.local.db.entity.movies.MovieData
import com.sunil.app.data.local.db.entity.movies.MovieDbData
import com.sunil.app.data.local.db.entity.movies.MovieRemoteKeyDbData
import com.sunil.app.domain.entity.movies.MovieEntity
import com.sunil.app.domain.model.Result

/**
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
interface MovieDataSource {

    interface Remote {
        suspend fun getMovies(page: Int, limit: Int): Result<List<MovieData>>
        suspend fun getMovies(movieIds: List<Int>): Result<List<MovieData>>
        suspend fun getMovie(movieId: Int): Result<MovieData>
        suspend fun search(query: String, page: Int, limit: Int): Result<List<MovieData>>
    }

    interface Local {
        fun movies(): PagingSource<Int, MovieDbData>
        suspend fun getMovies(): Result<List<MovieEntity>>
        suspend fun getMovie(movieId: Int): Result<MovieEntity>
        suspend fun saveMovies(movies: List<MovieData>)
        suspend fun getLastRemoteKey(): MovieRemoteKeyDbData?
        suspend fun saveRemoteKey(key: MovieRemoteKeyDbData)
        suspend fun clearMovies()
        suspend fun clearRemoteKeys()
    }
}
