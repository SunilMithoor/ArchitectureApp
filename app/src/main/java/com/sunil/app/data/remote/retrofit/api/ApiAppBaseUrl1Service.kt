package com.sunil.app.data.remote.retrofit.api

import com.sunil.app.domain.entity.response.GetAllDataResponse
import retrofit2.Response
import retrofit2.http.GET


/**
 * ApiAppBaseUrl1Service: Interface defining the API service for fetching data from the first base URL.
 *
 * This interface provides methods to interact with the API endpoints associated with the first base URL.
 *
 * @author Sunil
 * @since 1.0
 * @since 2025-01-28
 */
interface ApiAppBaseUrl1Service {

    /**
     * Fetches all data from the "/objects" endpoint.
     *
     * This method sends a GET request to the "/objects" endpoint and returns a [Response] object
     * containing the [GetAllDataResponse] if the request is successful, or an error if the request fails.
     *
     * @return A [Response] object containing the [GetAllDataResponse] or an error.
     */
    @GET("/objects")
    suspend fun getAllData(): Response<GetAllDataResponse>
}
