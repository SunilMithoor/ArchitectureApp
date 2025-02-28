package com.sunil.app.domain.repository.restful

import com.sunil.app.domain.entity.response.GetAllDataResponse
import com.sunil.app.domain.model.IOTaskResult
import kotlinx.coroutines.flow.Flow


interface RestfulRepository {

    suspend fun fetchAllData(): Flow<IOTaskResult<GetAllDataResponse>>
//    suspend fun fetchDataById(): Flow<Boolean>
//    suspend fun postData(): Flow<Boolean>
//    suspend fun updateData(): Flow<Boolean>
//    suspend fun updateSelectedData(): Flow<Boolean>
//    suspend fun deleteSelectedData(): Flow<Boolean>

}
