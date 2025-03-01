package com.sunil.app.presentation.viewmodel.restful

import com.sunil.app.base.BaseViewModel
import com.sunil.app.domain.entity.response.GetAllDataResponse
import com.sunil.app.domain.model.ViewState
import com.sunil.app.domain.usecase.None
import com.sunil.app.domain.usecase.restful.GetAllDataUseCase
import com.sunil.app.domain.utils.getViewStateFlowForNetworkCall
import com.sunil.app.presentation.extension.AppString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel responsible for managing the UI state and data related to the RESTful API.
 *
 * This ViewModel fetches data using the GetAllDataUseCase and exposes the UI state
 * through a StateFlow.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-02-28
 */
@HiltViewModel
class RestfulViewModel @Inject constructor(
    private val getAllDataUseCase: GetAllDataUseCase
) : BaseViewModel() {

    companion object {
        private const val TAG = "RestfulViewModel"
    }

    /**
     * Data class representing the UI state for the RESTful screen.
     *
     * @property allDataResponse The response data from the API, or null if not yet loaded.
     * @property isLoading True if data is currently being fetched, false otherwise.
     * @property errorMessage An error message to display, or null if no error.
     */
    data class RestfulUiState(
        val allDataResponse: GetAllDataResponse? = null,
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    /**
     * MutableStateFlow to hold and update the UI state.
     *
     * This is private to prevent external modification of the state.
     */
    private val _uiState = MutableStateFlow(RestfulUiState())

    /**
     * Publicly exposed StateFlow for observing the UI state.
     *
     * UI components should collect from this flow to update their state.
     */
    val uiState: StateFlow<RestfulUiState> = _uiState

    /**
     * Fetches all data from the API.
     *
     * This function launches a coroutine in the viewModelScope to fetch the data
     * and update the UI state accordingly.
     */
    fun getAllData() {
        coroutineScope.launch {
            getViewStateFlowForNetworkCall {
                getAllDataUseCase.execute(None)
            }.collect { dataState ->
                populateAllData(dataState)
            }
        }
    }

    /**
     * Updates the UI state based on the result of the data fetching operation.
     *
     * @param dataState The ViewState representing the result of the data fetching.
     */
    private fun populateAllData(dataState: ViewState<GetAllDataResponse>) {
        when (dataState) {
            is ViewState.Loading -> {
                Timber.tag(TAG).d("Loading")
                _uiState.update { currentState ->
                    // Update the state to show loading and clear any previous error message
                    currentState.copy(isLoading = true, errorMessage = null)
                }
            }

            is ViewState.Failure -> {
                Timber.tag(TAG).e(dataState.throwable, "Failure fetching data")
                _uiState.update { currentState ->
                    // Update the state to show the error message and stop loading
                    currentState.copy(
                        isLoading = false,
                        errorMessage = dataState.throwable.message
                            ?: codeSnippet.getString(AppString.failure)
                            ?: "Unknown error"
                    )
                }
                // Show the error message in a snackbar
                _message.value = dataState.throwable.message
                    ?: codeSnippet.getString(AppString.failure)
                            ?: "Unknown error"
            }

            is ViewState.Success -> {
                _uiState.update { currentState ->
                    // Update the state with the fetched data and stop loading
                    currentState.copy(
                        isLoading = false,
                        allDataResponse = dataState.data,
                        errorMessage = null
                    )
                }
                // Show the success message in a snackbar
                _message.value = codeSnippet.getString(AppString.success) ?: "Success"
            }
        }
    }
}
