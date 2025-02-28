package com.sunil.app.presentation.viewmodel.restful

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sunil.app.base.BaseViewModel
import com.sunil.app.domain.entity.response.GetAllDataResponse
import com.sunil.app.domain.model.ViewState
import com.sunil.app.domain.usecase.None
import com.sunil.app.domain.usecase.restful.GetAllDataUseCase
import com.sunil.app.domain.utils.getViewStateFlowForNetworkCall
import com.sunil.app.presentation.extension.AppString
import com.sunil.app.presentation.util.CodeSnippet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RestfulViewModel @Inject constructor(
    private val getAllDataUseCase: GetAllDataUseCase,
    codeSnippet: CodeSnippet
) : BaseViewModel(codeSnippet) {

    companion object {
        private const val TAG = "RestfulViewModel"
    }

    private val _allData: MutableLiveData<GetAllDataResponse> = MutableLiveData()
    val observeGetAllData: LiveData<GetAllDataResponse> get() = _allData


    fun getAllData() {
        coroutineScope.launch {
            getViewStateFlowForNetworkCall {
                getAllDataUseCase.execute(None)
            }.collect {
                populateAllData(it)
            }
        }
    }


    private fun populateAllData(dataState: ViewState<GetAllDataResponse>) {
        when (dataState) {
            is ViewState.Loading -> {
                Timber.tag(TAG).d("Loading")
//                _message.postValue("Loading")
            }

            is ViewState.RenderFailure -> {
                Timber.tag(TAG).d("Failure ${dataState.throwable.message}")
                _message.value = dataState.throwable.message ?: "Failure"
            }

            is ViewState.RenderSuccess<GetAllDataResponse> -> {
                _message.value = codeSnippet.getString(AppString.success) ?: "Success"
                _allData.postValue(dataState.output)
                dataState.output
            }
        }
    }


}
