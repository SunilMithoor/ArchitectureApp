package com.sunil.app.domain.manager


import com.sunil.app.domain.entity.response.GetAllDataResponse
import com.sunil.app.domain.model.IOTaskResult
import com.sunil.app.domain.repository.restful.RestfulRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Data manager responsible for handling data operations related to the RESTful API.
 *
 * This class acts as an intermediary between the presentation layer (e.g., ViewModels)
 * and the data layer (RestfulRepository). It encapsulates the logic for fetching
 * data and potentially applying business rules or transformations.
 */
class RestfulDataManager @Inject constructor(
    private val restfulRepository: RestfulRepository,
) : RestfulRepository { // 1. Introduce an interface

    /**
     * Fetches all data from the RESTful API.
     *
     * @return A Flow emitting IOTaskResult, which can be either a success with
     *         GetAllDataResponse or an error.
     */
    override suspend fun fetchAllData(): Flow<IOTaskResult<GetAllDataResponse>> =
        restfulRepository.fetchAllData()
}
