package com.sunil.app.data.repository

import com.sunil.app.data.remote.retrofit.datasource.RestfulRemoteDataSource
import com.sunil.app.domain.entity.response.GetAllDataResponse
import com.sunil.app.domain.model.IOTaskResult
import com.sunil.app.domain.repository.RestfulRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RestfulRepositoryImpl @Inject constructor(
    private val restfulRemoteDataSource: RestfulRemoteDataSource
) : RestfulRepository {

    override suspend fun fetchAllData(): Flow<IOTaskResult<GetAllDataResponse>> =
        restfulRemoteDataSource.fetchAllData()

}
