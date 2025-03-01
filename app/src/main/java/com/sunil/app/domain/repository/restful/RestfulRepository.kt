package com.sunil.app.domain.repository.restful

import com.sunil.app.domain.entity.response.GetAllDataResponse
import com.sunil.app.domain.model.IOTaskResult
import kotlinx.coroutines.flow.Flow



/**
 * Interface for interacting with RESTful APIs to manage data.
 *
 * This repository defines the contract for fetching, creating, updating, and deleting data
 *from a remote server. It uses Kotlin Coroutines Flow to handle asynchronous operations
 * and provides results wrapped in [IOTaskResult] to manage success and error states.
 */
interface RestfulRepository {

    /**
     * Fetches all data from the remote server.
     *
     * @return A [Flow] emitting [IOTaskResult] containing either a successful [GetAllDataResponse]
     *         or an error state.
     */
    suspend fun fetchAllData(): Flow<IOTaskResult<GetAllDataResponse>>

    /**
     * Fetches data by its unique identifier.
     *
     * @param id The unique identifier of the data to fetch.
     * @return A [Flow] emitting [IOTaskResult] containing either the requested data or an error state.
     */
    //suspend fun fetchDataById(id: String): Flow<IOTaskResult<DataByIdResponse>> // Example with a placeholder DataByIdResponse

    /**
     * Creates new data on the remote server.
     *
     * @param data The data to be created.
     * @return A [Flow] emitting [IOTaskResult] indicating the success or failure of the operation.
     */
    //suspend fun postData(data: NewData): Flow<IOTaskResult<Unit>> // Example with a placeholder NewData and Unit for no response body

    /**
     * Updates existing data on the remote server.
     *
     * @param id The unique identifier of the data to update.
     * @param updatedData The updated data.
     * @return A [Flow] emitting [IOTaskResult] indicating the success or failure of the operation.
     */
    //suspend fun updateData(id: String, updatedData: UpdatedData): Flow<IOTaskResult<Unit>> // Example with a placeholder UpdatedData and Unit for no response body

    /**
     * Updates specific fields of existing data on the remote server.
     *
     * @param id The unique identifier of the data to update.
     * @param partialData The partial data to update.
     * @return A [Flow] emitting [IOTaskResult] indicating the success or failure of the operation.
     */
    //suspend fun updateSelectedData(id: String, partialData: PartialData): Flow<IOTaskResult<Unit>> // Example with a placeholder PartialData and Unit for no response body

    /**
     * Deletes data from the remote server.
     *
     * @param id The unique identifier of the data to delete.
     * @return A [Flow] emitting [IOTaskResult] indicating the success or failure of the operation.
     */
    //suspend fun deleteSelectedData(id: String): Flow<IOTaskResult<Unit>> // Example with Unit for no response body
}
