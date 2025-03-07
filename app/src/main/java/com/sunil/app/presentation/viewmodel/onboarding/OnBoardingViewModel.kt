package com.sunil.app.presentation.viewmodel.onboarding

import com.sunil.app.base.BaseViewModel
import com.sunil.app.domain.usecase.None
import com.sunil.app.domain.usecase.onboarding.GetDarkModeUseCase
import com.sunil.app.domain.usecase.onboarding.SetDarkModeUseCase
import com.sunil.app.presentation.ui.screens.main.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


/*** ViewModel responsible for managing the onboarding-related UI state and data.
 *
 * This ViewModel handles fetching and updating the dark mode preference.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-02-28
 */
@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val getDarkModeUseCase: GetDarkModeUseCase,
    private val setDarkModeUseCase: SetDarkModeUseCase,
) : BaseViewModel() {


    init {
        fetchDarkModePreference()
    }

    /**
     * StateFlow for managing the UI state related to getting dark mode.
     *
     * This is used to represent the overall state of the dark mode preference retrieval.
     */
    private val _uiGetDarkModeState: MutableStateFlow<MainState> =
        MutableStateFlow(MainState())

    /**
     * Publicly exposed StateFlow for observing the UI state related to getting dark mode.
     *
     * UI components can collect from this flow to update their state.
     */
    val uiGetDarkModeState = _uiGetDarkModeState.asStateFlow()

    /**
     * Fetches the dark mode preference.
     *
     * This function launches a coroutine in the viewModelScope to fetch the dark mode
     * preference and update the [uiGetDarkModeState] LiveData accordingly.
     */
    fun fetchDarkModePreference() {
        coroutineScope.launch {
            getDarkModeUseCase.execute(None)
                .onEach { result ->
                    _uiGetDarkModeState.update { it.copy(darkMode = result) }
                }
        }
    }


    /**
     * StateFlow for managing the UI state related to getting dark mode.
     *
     * This is used to represent the overall state of the dark mode preference retrieval.
     */
    private val _uiSetDarkModeState: MutableStateFlow<MainState> =
        MutableStateFlow(MainState())

    /**
     * Publicly exposed StateFlow for observing the UI state related to getting dark mode.
     *
     * UI components can collect from this flow to update their state.
     */
    val uiSetDarkModeState = _uiSetDarkModeState.asStateFlow()

    /**
     * Updates the dark mode preference.
     *
     * This function launches a coroutine in the viewModelScope to update the dark mode
     * preference and update the [_uiSetDarkModeState] LiveData accordingly.
     *
     * @param isDarkModeEnabled The new dark mode preference.
     */
//    fun updateDarkModePreference(isDarkModeEnabled: Boolean) {
//        coroutineScope.launch {
//            setDarkModeUseCase.execute(isDarkModeEnabled)
//                .onEach { result ->
//                    _uiSetDarkModeState.update { it.copy(darkMode = result) }
//                    _uiGetDarkModeState.update { it.copy(darkMode = result) } // Ensure the UI gets updated
//                }
//                // Launch the flow collection in the viewModelScope.
//                .launchIn(coroutineScope)
//        }
//    }

    fun updateDarkModePreference(isDarkModeEnabled: Boolean) {
        coroutineScope.launch {
            setDarkModeUseCase.execute(isDarkModeEnabled)
                .collect { result ->
                    _uiSetDarkModeState.update { it.copy(darkMode = result) }
                    _uiGetDarkModeState.update { it.copy(darkMode = result) }
                }
        }
    }


}
