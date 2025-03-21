package com.sunil.app.presentation.ui.screens.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.sunil.app.domain.utils.NetworkMonitor
import com.sunil.app.presentation.ui.screens.base.BaseActivity
import com.sunil.app.presentation.ui.screens.base.BaseView
import com.sunil.app.presentation.ui.theme.AppTheme
import com.sunil.app.presentation.ui.theme.AppThemeState
import com.sunil.app.presentation.ui.theme.ColorPallet
import com.sunil.app.presentation.ui.theme.SystemUiController
import com.sunil.app.presentation.ui.widget.CustomSnackBar
import com.sunil.app.presentation.ui.widget.NoInternetConnectionBanner
import com.sunil.app.presentation.viewmodel.onboarding.OnBoardingViewModel
import com.sunil.custom_snackbar.presentation.rememberComposeModifiedSnackbarState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    private val viewModel by viewModels<OnBoardingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val navController = rememberNavController()
            val uiDarkModeState by viewModel.uiGetDarkModeState.collectAsState()


            AppTheme(uiDarkModeState.darkMode) {
                Column {
                    val networkStatus by networkMonitor.networkState.collectAsState(null)
                    networkStatus?.let {
                        if (it.isOnline.not()) {
                            NoInternetConnectionBanner()
                        }
                    }
                    MainGraph(
                        mainNavController = navController,
                        darkMode = uiDarkModeState.darkMode,
                        onThemeUpdated = {
                            enableDarkMode()
                        }
                    )
                }
//
            }
        }
    }

    private fun enableDarkMode() {
        viewModel.fetchDarkModePreference()
        val darKMode = viewModel.uiGetDarkModeState.value
        if (darKMode.darkMode) {
            viewModel.updateDarkModePreference(false)
        } else {
            viewModel.updateDarkModePreference(true)
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
