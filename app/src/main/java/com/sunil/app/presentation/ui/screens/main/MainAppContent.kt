package com.sunil.app.presentation.ui.screens.main

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.guru.composecookbook.ui.utils.TestTags
import com.sunil.app.R
import com.sunil.app.presentation.extension.AppString
import com.sunil.app.presentation.ui.enums.BottomNavType
import com.sunil.app.presentation.ui.screens.notifications.NotificationsScreenContent
import com.sunil.app.presentation.ui.theme.AppThemeState
import com.sunil.custom_snackbar.presentation.ComposeModifiedSnackbar
import com.sunil.custom_snackbar.presentation.rememberComposeModifiedSnackbarState
import com.sunil.custom_snackbar.util.ComposeModifiedSnackbarColor
import com.sunil.custom_snackbar.util.ComposeModifiedSnackbarPosition
import com.sunil.custom_snackbar.util.ComposeModifierSnackbarDuration
import kotlinx.coroutines.launch

@Composable
fun MainAppContent(appThemeState: MutableState<AppThemeState>) {

    Scaffold(
        modifier = Modifier.testTag(TestTags.HOME_SCREEN_ROOT),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Home") },
                actions = {
                    IconButton(onClick = {
                        appThemeState.value = appThemeState
                            .value.copy(darkTheme = !appThemeState.value.darkTheme)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chevron_left_back),
                            contentDescription = stringResource(id = R.string.dark_theme)
                        )
                    }
                },
            )
        },
        content = { paddingValues ->
            NotificationsScreenContent(
                modifier = Modifier.padding(paddingValues)
            )
//            HomeScreenContent(
//                isDarkTheme = appThemeState.value.darkTheme,
//                showMenu = showMenu,
//                modifier = Modifier.padding(paddingValues),
//                onPalletChange = { newPalletSelected ->
//                    // Events can be and should be passed to as upper layer as possible here
//                    // we are just passing to till HomeScreen.
//                    appThemeState.value = appThemeState.value.copy(pallet = newPalletSelected)
//                    showMenu.value = false
//                }
//            )
        }
    )


}

//@Composable
//fun MainAppContent(appThemeState: MutableState<AppThemeState>) {
//    //Default home screen state is always HOME
//    val homeScreenState = rememberSaveable { mutableStateOf(BottomNavType.HOME) }
//    val bottomNavBarContentDescription = stringResource(id = AppString.bottom_navigation_bar_actions)
//    val chooseColorBottomModalState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
//
//    val coroutineScope = rememberCoroutineScope()
//
//    ModalBottomSheetLayout(
//        sheetState = chooseColorBottomModalState,
//        sheetContent = {
//
//            //Modal used only when user use talkback for the sake of accessibility
////            PalletMenu(
////                modifier = Modifier.align(Alignment.CenterHorizontally)
////            ) { newPalletSelected ->
////                appThemeState.value = appThemeState.value.copy(pallet = newPalletSelected)
////                coroutineScope.launch {
////                    chooseColorBottomModalState.hide()
////                }
////            }
//        }
//    ) {
//        val config = LocalConfiguration.current
//        val orientation = config.orientation
//        if (orientation == ORIENTATION_PORTRAIT) {
//            Column {
////                HomeScreenContent(
////                    homeScreen = homeScreenState.value,
////                    appThemeState = appThemeState,
////                    chooseColorBottomModalState = chooseColorBottomModalState,
////                    modifier = Modifier.weight(1f)
////                )
//                BottomNavigationContent(
//                    modifier = Modifier
//                        .semantics { contentDescription = bottomNavBarContentDescription }
//                        .testTag(TestTags.BOTTOM_NAV_TEST_TAG),
//                    homeScreenState = homeScreenState
//                )
//            }
//        } else {
//            Row(modifier = Modifier.fillMaxSize()) {
//                NavigationRailContent(
//                    modifier = Modifier
//                        .semantics { contentDescription = bottomNavBarContentDescription }
//                        .testTag(TestTags.BOTTOM_NAV_TEST_TAG),
//                    homeScreenState = homeScreenState
//                )
//                HomeScreenContent(
//                    homeScreen = homeScreenState.value,
//                    appThemeState = appThemeState,
//                    chooseColorBottomModalState = chooseColorBottomModalState,
//                    modifier = Modifier.weight(1f)
//                )
//            }
//        }
//    }
//
//}
