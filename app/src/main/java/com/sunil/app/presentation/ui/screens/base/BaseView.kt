package com.sunil.app.presentation.ui.screens.base

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.sunil.app.presentation.ui.theme.AppThemeState
import com.sunil.app.presentation.ui.theme.ColorPallet
import com.sunil.app.presentation.ui.theme.MaterialAppTheme
import com.sunil.app.presentation.ui.theme.SystemUiController
import com.sunil.app.presentation.ui.theme.blue700
import com.sunil.app.presentation.ui.theme.green700
import com.sunil.app.presentation.ui.theme.orange700
import com.sunil.app.presentation.ui.theme.purple700
import com.sunil.custom_snackbar.presentation.rememberComposeModifiedSnackbarState

@Composable
fun BaseView(
    appThemeState: AppThemeState,
    systemUiController: SystemUiController?,
    content: @Composable () -> Unit
) {
    val color = when (appThemeState.pallet) {
        ColorPallet.GREEN -> green700
        ColorPallet.BLUE -> blue700
        ColorPallet.ORANGE -> orange700
        ColorPallet.PURPLE -> purple700
        else -> green700
    }
    val state = rememberComposeModifiedSnackbarState()

    MaterialAppTheme(
        darkTheme = appThemeState.darkTheme, colorPallet = appThemeState.pallet
    ) {
        systemUiController?.setStatusBarColor(
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            darkIcons = appThemeState.darkTheme
        )
        content()
    }
}
