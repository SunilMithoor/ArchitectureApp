package com.sunil.app.presentation.ui.screens.notifications


import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guru.composecookbook.ui.utils.TestTags
import com.sunil.app.presentation.ui.theme.typography

@Composable
fun NotificationsScreen() {
    Scaffold(
        modifier = Modifier.testTag(TestTags.WIDGET_SCREEN_ROOT),
        topBar = {
            TopAppBar(
                title = { Text(text = " Notifications") },
            )
        },
        content = { paddingValues ->
            NotificationsScreenContent(
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}

@Composable
fun NotificationsScreenContent(
    modifier: Modifier = Modifier,
) {
    Text(text = "Notifications", style = typography.h5, modifier = Modifier.padding(8.dp))
}


@Preview
@Composable
fun PreviewScreen() {
    NotificationsScreen()
}
