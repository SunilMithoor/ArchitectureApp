package com.sunil.app.domain.usecase

import com.sunil.app.domain.entity.response.GetAllDataResponse
import com.sunil.app.domain.manager.RestfulDataManager
import com.sunil.app.domain.model.IOTaskResult
import com.sunil.app.domain.repository.RestfulRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GetAllDataUseCase @Inject constructor(private val restfulDataManager: RestfulDataManager) :
    IUseCaseFlow<None, GetAllDataResponse> {

    override suspend fun execute(input: None): Flow<IOTaskResult<GetAllDataResponse>> =
        restfulDataManager.fetchAllData()

}
