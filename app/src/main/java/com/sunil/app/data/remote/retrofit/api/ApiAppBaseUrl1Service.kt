package com.sunil.app.data.remote.retrofit.api

import com.sunil.app.domain.entity.response.GetAllDataResponse
import retrofit2.Response
import retrofit2.http.GET


interface ApiAppBaseUrl1Service {

    @GET("/objects")
    suspend fun getAllDataResponse(): Response<GetAllDataResponse>


}
