package com.sunil.app.data.remote.retrofit.datasource.restful


import com.sunil.app.data.remote.retrofit.api.ApiAppBaseUrl1Service
import com.sunil.app.data.remote.retrofit.datasource.ApiWrapper
import javax.inject.Inject

class RestfulRemoteDataSource @Inject constructor(private val apiAppBaseUrl1Service: ApiAppBaseUrl1Service) :
    ApiWrapper() {

    suspend fun fetchAllData() = getResult { apiAppBaseUrl1Service.getAllDataResponse() }

}
