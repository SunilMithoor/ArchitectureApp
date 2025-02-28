package com.sunil.app.presentation.ui.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.sunil.app.data.utils.AppConstants.DARK_MODE
import com.sunil.app.presentation.ui.screens.base.BaseView
import com.sunil.app.presentation.ui.theme.AppThemeState
import com.sunil.app.presentation.ui.theme.ColorPallet
import com.sunil.app.presentation.ui.theme.SystemUiController
import com.sunil.app.presentation.viewmodel.onboarding.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<OnBoardingViewModel>()
    private fun enableDarkMode(enable: Boolean) = viewModel.setDarkMode(enable)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
             val systemUiController = remember { SystemUiController(window) }
             val appTheme = remember { mutableStateOf(AppThemeState()) }
             val navController = rememberNavController()
             val uiDarkModeState by viewModel.uiGetDarkModeState.collectAsState()
             var darkMode by remember { mutableStateOf(uiDarkModeState.darkMode) }

            BaseView(appTheme.value, systemUiController) {
                MainGraph(
                    mainNavController = navController,
                    darkMode = darkMode,
                    onThemeUpdated = {
                        val updated = !darkMode
                        enableDarkMode(updated)
                        darkMode = updated
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val appThemeState = remember { mutableStateOf(AppThemeState(false, ColorPallet.GREEN)) }
    BaseView(appThemeState.value, null) {
        MainAppContent(appThemeState)
    }
}
