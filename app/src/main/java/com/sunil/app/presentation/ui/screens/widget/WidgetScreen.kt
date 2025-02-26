package com.sunil.app.presentation.ui.screens.widget

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
fun WidgetScreen() {
    Scaffold(
        modifier = Modifier.testTag(TestTags.WIDGET_SCREEN_ROOT),
        topBar = {
            TopAppBar(
                title = { Text(text = " Widgets") },
            )
        },
        content = { paddingValues ->
            WidgetScreenContent(
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}

@Composable
fun WidgetScreenContent(
    modifier: Modifier = Modifier,
) {
    Text(text = "Widget", style = typography.h5, modifier = Modifier.padding(8.dp))
}


@Preview
@Composable
fun PreviewScreen() {
    WidgetScreen()
}
