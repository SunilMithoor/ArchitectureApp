package com.sunil.app.domain.usecase.restful

import com.sunil.app.domain.entity.response.GetAllDataResponse
import com.sunil.app.domain.manager.RestfulDataManager
import com.sunil.app.domain.model.IOTaskResult
import com.sunil.app.domain.usecase.IUseCaseFlow
import com.sunil.app.domain.usecase.None
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Use case for fetching all data from a RESTful API.
 *
 * This class encapsulates the logic for retrieving all data, abstracting the data source
 * and providing a clean interface for the presentation layer.
 */
@Singleton
class GetAllDataUseCase @Inject constructor(private val restfulDataManager: RestfulDataManager) :
    IUseCaseFlow<None, GetAllDataResponse> {

    override suspend fun execute(input: None): Flow<IOTaskResult<GetAllDataResponse>> =
        restfulDataManager.fetchAllData()

}
