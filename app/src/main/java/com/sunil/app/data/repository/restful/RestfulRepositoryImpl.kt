package com.sunil.app.data.repository.restful

import com.sunil.app.data.remote.retrofit.datasource.restful.RestfulRemoteDataSource
import com.sunil.app.domain.entity.response.GetAllDataResponse
import com.sunil.app.domain.model.IOTaskResult
import com.sunil.app.domain.repository.restful.RestfulRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RestfulRepositoryImpl @Inject constructor(
    private val restfulRemoteDataSource: RestfulRemoteDataSource
) : RestfulRepository {

    override suspend fun fetchAllData(): Flow<IOTaskResult<GetAllDataResponse>> =
        restfulRemoteDataSource.fetchAllData()

}
