package com.sunil.app.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunil.app.presentation.extension.AppString
import com.sunil.app.presentation.util.CodeSnippet
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

abstract class BaseViewModel(
    protected val codeSnippet: CodeSnippet
) : ViewModel() {

    companion object {
        private const val TAG = "BaseViewModel"
    }

    val _message: MutableLiveData<String> = MutableLiveData()
    val messageLiveData: LiveData<String> get() = _message

    val errorMessageResIdLiveData = MutableLiveData<Int>()
    val validationResultLiveData = MutableLiveData<Boolean>()


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        // Handle the exception here
        Timber.tag(TAG).e(throwable)
        _message.postValue(codeSnippet.getStrings(AppString.internet_too_slow))
    }
    val coroutineScope = CoroutineScope(Dispatchers.IO + exceptionHandler)

}
