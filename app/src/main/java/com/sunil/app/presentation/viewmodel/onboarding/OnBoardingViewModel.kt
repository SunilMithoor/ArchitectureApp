package com.sunil.app.presentation.viewmodel.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sunil.app.base.BaseViewModel
import com.sunil.app.domain.usecase.None
import com.sunil.app.domain.usecase.onboarding.GetDarkModeUseCase
import com.sunil.app.domain.usecase.onboarding.SetDarkModeUseCase
import com.sunil.app.presentation.ui.screens.main.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    /**
     * MutableLiveData to hold the result of whether the user is first-time or not.
     *
     * This is private to prevent external modification.
     */
    private val _isUserFirstTimeLaunch = MutableLiveData<Boolean>()

    /**
     * Publicly exposed LiveData for observing whether the user is first-time or not.
     *
     * UI components should observe this LiveData to update their state.
     */
    val isUserFirstTimeLaunch: LiveData<Boolean> = _isUserFirstTimeLaunch

    /**
     * MutableLiveData to hold the result of setting the dark mode preference.
     *
     * This is private to prevent external modification.
     */
    private val _isDarkModeSet = MutableLiveData<Boolean>()

    /**
     * Publicly exposed LiveData for observing the result of setting the dark mode preference.
     *
     * UI components should observe this LiveData to update their state.
     */
    val isDarkModeSet: LiveData<Boolean> = _isDarkModeSet

    /**
     * StateFlow for managing the UI state related to getting dark mode.
     *
     * This is used to represent the overall state of the dark mode preference retrieval.
     */
    private val _uiGetDarkModeState = MutableStateFlow<MainState>(MainState())

    /**
     * Publicly exposed StateFlow for observing the UI state related to getting dark mode.
     *
     * UI components can collect from this flow to update their state.
     */
    val uiGetDarkModeState: StateFlow<MainState> = _uiGetDarkModeState.asStateFlow()

    /**
     * Fetches the dark mode preference.
     *
     * This function launches a coroutine in the viewModelScope to fetch the dark mode
     * preference and update the [_isUserFirstTimeLaunch] LiveData accordingly.
     */
    fun fetchDarkModePreference() {
        coroutineScope.launch {
            getDarkModeUseCase.execute(None)
                .onEach { result ->
                    when (result) {
                        true -> {
                            // Update the state with the fetched dark mode preference.
                            _uiGetDarkModeState.value = MainState(darkMode = result ?: false)
                        }

                        false -> {
                            // Update the state with the fetched dark mode preference.
                            _uiGetDarkModeState.value = MainState(darkMode = result ?: false)
                        }
                    }
                }
                // Launch the flow collection in the viewModelScope.
                .launchIn(coroutineScope)
        }
    }

    /**
     * Updates the dark mode preference.
     *
     * This function launches a coroutine in the viewModelScope to update the dark mode
     * preference and update the [_isDarkModeSet] LiveData accordingly.
     *
     * @param isDarkModeEnabled The new dark mode preference.
     */
    fun updateDarkModePreference(isDarkModeEnabled: Boolean) {
        coroutineScope.launch {
            setDarkModeUseCase.execute(isDarkModeEnabled)
                .onEach { result ->
                    when (result) {
                        true -> {
                            // Update the state with the fetched dark mode preference.
                            _isDarkModeSet.value = result ?: false
                        }

                        false -> {
                            // Update the state with the fetched dark mode preference.
                            _isDarkModeSet.value = result ?: false
                        }
                    }
                }
                // Launch the flow collection in the viewModelScope.
                .launchIn(coroutineScope)
        }
    }
}
