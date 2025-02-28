package com.sunil.app.data.remote.retrofit.datasource.movies

import com.sunil.app.data.local.db.entity.movies.MovieData
import com.sunil.app.data.remote.retrofit.api.ApiAppBaseUrl1Service
import com.sunil.app.data.remote.retrofit.api.ApiAppBaseUrl3Service
import com.sunil.app.data.remote.retrofit.datasource.ApiWrapper
import com.sunil.app.data.utils.safeApiCall
import com.sunil.app.domain.model.Result
import javax.inject.Inject


/**
 * @author Sunil
 * @version 1.0
 * @since 2025-02-16
 */

class MovieRemoteDataSource(
    private val apiAppBaseUrl3Service: ApiAppBaseUrl3Service
) : MovieDataSource.Remote {

    override suspend fun getMovies(page: Int, limit: Int): Result<List<MovieData>> = safeApiCall {
        apiAppBaseUrl3Service.getMovies(page, limit)
    }

    override suspend fun getMovies(movieIds: List<Int>): Result<List<MovieData>> = safeApiCall {
        apiAppBaseUrl3Service.getMovies(movieIds)
    }

    override suspend fun getMovie(movieId: Int): Result<MovieData> = safeApiCall {
        apiAppBaseUrl3Service.getMovie(movieId)
    }

    override suspend fun search(query: String, page: Int, limit: Int): Result<List<MovieData>> = safeApiCall {
        apiAppBaseUrl3Service.search(query, page, limit)
    }
}
