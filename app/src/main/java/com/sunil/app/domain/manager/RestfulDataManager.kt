package com.sunil.app.domain.manager


import com.sunil.app.domain.entity.response.GetAllDataResponse
import com.sunil.app.domain.model.IOTaskResult
import com.sunil.app.domain.repository.RestfulRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RestfulDataManager @Inject constructor(
    private val restfulRepository: RestfulRepository,
) : RestfulRepository {

    override suspend fun fetchAllData(): Flow<IOTaskResult<GetAllDataResponse>> =
        restfulRepository.fetchAllData()


}
