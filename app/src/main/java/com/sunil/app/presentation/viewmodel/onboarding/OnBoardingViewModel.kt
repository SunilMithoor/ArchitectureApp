package com.sunil.app.presentation.viewmodel.onboarding

import androidx.lifecycle.MutableLiveData
import com.sunil.app.base.BaseViewModel
import com.sunil.app.domain.usecase.None
import com.sunil.app.domain.usecase.onboarding.GetDarkModeUseCase
import com.sunil.app.domain.usecase.onboarding.SetDarkModeUseCase
import com.sunil.app.domain.utils.getViewStateFlowForAll
import com.sunil.app.presentation.ui.screens.main.MainState
import com.sunil.app.presentation.ui.screens.movies.search.SearchUiState
import com.sunil.app.presentation.util.CodeSnippet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val getDarkModeUseCase: GetDarkModeUseCase,
    private val setDarkModeUseCase: SetDarkModeUseCase,
    codeSnippet: CodeSnippet
) : BaseViewModel(codeSnippet) {


    val observeIsUserFirstTimeLaunchLive: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val observeSetUserFirstTimeLaunchLive: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val _uiGetDarkModeState: MutableStateFlow<MainState> = MutableStateFlow(
        MainState()
    )
    val uiGetDarkModeState = _uiGetDarkModeState.asStateFlow()


    fun getDarkMode() {
        coroutineScope.launch {
            getViewStateFlowForAll {
                getDarkModeUseCase.execute(None)
            }.collect {
                populateDarkModeData(it)
            }
        }
    }


    fun setDarkMode(value: Boolean) {
        coroutineScope.launch {
            getViewStateFlowForAll {
                setDarkModeUseCase.execute(value)
            }.collect {
                getDarkModeData(it)
            }
        }
    }

    private fun populateDarkModeData(any: Any) {
        if (any is Boolean) {
            observeIsUserFirstTimeLaunchLive.value = any.toString().toBoolean()
        }
    }

    private fun getDarkModeData(any: Any) {
        if (any is Boolean) {
            observeSetUserFirstTimeLaunchLive.value = any.toString().toBoolean()
        }
    }
}
