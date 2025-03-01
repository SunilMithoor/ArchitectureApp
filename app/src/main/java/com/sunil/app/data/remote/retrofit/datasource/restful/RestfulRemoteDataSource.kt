package com.sunil.app.data.remote.retrofit.datasource.restful


import com.sunil.app.data.remote.retrofit.api.ApiAppBaseUrl1Service
import com.sunil.app.data.remote.retrofit.datasource.ApiWrapper
import com.sunil.app.domain.entity.response.GetAllDataResponse
import com.sunil.app.domain.model.IOTaskResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

/**
 * RestfulRemoteDataSource is responsible for fetching data from the remote API.
 * It uses the ApiAppBaseUrl1Service to make network requests and the ApiWrapper
 * to handle common API response logic.
 */
class RestfulRemoteDataSource @Inject constructor(
    private val apiAppBaseUrl1Service: ApiAppBaseUrl1Service
) : ApiWrapper() {

    /**
     * Fetches all data from the remote API.
     *
     * @return A Result object containing either the data or an error.
     */
    suspend fun fetchAllData(): Flow<IOTaskResult<GetAllDataResponse>> {
        return getResult { apiAppBaseUrl1Service.getAllData() }
    }
}
