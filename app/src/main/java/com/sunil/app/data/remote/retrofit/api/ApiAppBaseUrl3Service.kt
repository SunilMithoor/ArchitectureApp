package com.sunil.app.data.remote.retrofit.api

import com.sunil.app.data.local.db.entity.movies.MovieData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Sunil on 13/01/2025
 */
interface ApiAppBaseUrl3Service {

    @GET("/movies?&_sort=category,id")
    suspend fun getMovies(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int,
    ): List<MovieData>

    @GET("/movies")
    suspend fun getMovies(@Query("id") movieIds: List<Int>): List<MovieData>

    @GET("/movies/{id}")
    suspend fun getMovie(@Path("id") movieId: Int): MovieData

    @GET("/movies")
    suspend fun search(
        @Query("q") query: String,
        @Query("_page") page: Int,
        @Query("_limit") limit: Int,
    ): List<MovieData>
}
