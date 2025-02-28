package com.sunil.app.presentation.ui.screens.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.guru.composecookbook.ui.utils.TestTags
import com.sunil.app.R
import com.sunil.app.presentation.extension.AppDrawable
import com.sunil.app.presentation.extension.AppString
import com.sunil.app.presentation.ui.enums.BottomNavType
import com.sunil.app.presentation.ui.util.RotateIcon

@Composable
fun NavigationRailContent(
    modifier: Modifier,
    homeScreenState: MutableState<BottomNavType>,
) {
    var animate by remember { mutableStateOf(false) }
    NavigationRail(
        modifier = modifier,
    ) {
        NavigationRailItem(
            icon = {
                Icon(
                    painter = painterResource(id = AppDrawable.ic_chevron_left_back),
                    contentDescription = null,
                    tint = LocalContentColor.current.copy(
                        alpha =
                        LocalContentAlpha.current
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            selected = homeScreenState.value == BottomNavType.HOME,
            onClick = {
                homeScreenState.value = BottomNavType.HOME
                animate = false
            },
            label = {
                Text(
                    text = stringResource(id = AppString.navigation_item_home),
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_HOME_TEST_TAG)
        )
        NavigationRailItem(
            icon = {
                Icon(
                    painter = painterResource(id = AppDrawable.ic_chevron_left_back),
                    contentDescription = null,
                    tint = LocalContentColor.current.copy(
                        alpha =
                        LocalContentAlpha.current
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            },

            selected = homeScreenState.value == BottomNavType.NOTIFICATIONS,
            onClick = {
                homeScreenState.value = BottomNavType.NOTIFICATIONS
                animate = false
            },
            label = {
                Text(
                    text = stringResource(id = AppString.navigation_item_notifications),
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_WIDGETS_TEST_TAG)
        )
        NavigationRailItem(
            icon = {
                RotateIcon(
                    state = animate,
                    asset = Icons.Default.PlayArrow,
                    angle = 720f,
                    duration = 2000
                )
            },
            selected = homeScreenState.value == BottomNavType.PROFILE,
            onClick = {
                homeScreenState.value = BottomNavType.PROFILE
                animate = true
            },
            label = {
                Text(
                    text = stringResource(id = AppString.navigation_item_profile),
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_ANIM_TEST_TAG)

        )
    }
}
