package com.sunil.app.data.repository.restful

import com.sunil.app.data.remote.retrofit.datasource.restful.RestfulRemoteDataSource
import com.sunil.app.domain.entity.response.GetAllDataResponse
import com.sunil.app.domain.model.IOTaskResult
import com.sunil.app.domain.repository.restful.RestfulRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Concrete implementation of [RestfulRepository] that interacts with a remote data source.
 *
 * This class handles the fetching of data from the remote data source and provides it to the
 * domain layer.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
class RestfulRepositoryImpl @Inject constructor(
    private val remoteDataSource: RestfulRemoteDataSource // Renamed for clarity
) : RestfulRepository {

    /**
     * Fetches all data from the remote data source.
     *
     * @return A [Flow] emitting [IOTaskResult] containing [GetAllDataResponse] or an error.
     */
    override suspend fun fetchAllData(): Flow<IOTaskResult<GetAllDataResponse>> =
        remoteDataSource.fetchAllData() // Using the renamed variable

}
