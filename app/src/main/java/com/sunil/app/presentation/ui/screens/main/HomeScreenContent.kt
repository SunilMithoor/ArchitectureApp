package com.sunil.app.presentation.ui.screens.main

import android.os.Build
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sunil.app.presentation.ui.enums.BottomNavType
import com.sunil.app.presentation.ui.screens.home.HomeScreen
import com.sunil.app.presentation.ui.screens.profile.ProfileScreen
import com.sunil.app.presentation.ui.screens.widget.WidgetScreen
import com.sunil.app.presentation.ui.theme.AppThemeState
import com.sunil.app.presentation.ui.theme.ColorPallet
import com.sunil.app.presentation.ui.theme.blue500
import com.sunil.app.presentation.ui.theme.green500
import com.sunil.app.presentation.ui.theme.orange500
import com.sunil.app.presentation.ui.theme.purple

@Composable
fun HomeScreenContent(
    homeScreen: BottomNavType,
    appThemeState: MutableState<AppThemeState>,
    chooseColorBottomModalState: ModalBottomSheetState, //use for a11y
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Crossfade(homeScreen) { screen ->
            Surface(color = MaterialTheme.colorScheme.background) {
                when (screen) {
                    BottomNavType.HOME -> HomeScreen()
                    BottomNavType.NOTIFICATIONS -> WidgetScreen()
                    BottomNavType.PROFILE -> ProfileScreen()
                }
            }
        }
    }
}


@Composable
fun PalletMenu(
    modifier: Modifier,
    onPalletChange: (ColorPallet) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .animateContentSize(),
        ) {
            MenuItem(green500, "Green") {
                onPalletChange.invoke(ColorPallet.GREEN)
            }
            MenuItem(purple, "Purple") {
                onPalletChange.invoke(ColorPallet.PURPLE)
            }
            MenuItem(orange500, "Orange") {
                onPalletChange.invoke(ColorPallet.ORANGE)
            }
            MenuItem(blue500, "Blue") {
                onPalletChange.invoke(ColorPallet.BLUE)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MenuItem(dynamicLightColorScheme(LocalContext.current).primary, "Dynamic") {
                    onPalletChange.invoke(ColorPallet.WALLPAPER)
                }
            }
        }
    }
}

@Composable
fun MenuItem(color: Color, name: String, onPalletChange: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onPalletChange),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.FiberManualRecord,
            tint = color,
            contentDescription = null
        )
        Text(text = name, modifier = Modifier.padding(8.dp))
    }
}
