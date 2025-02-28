package com.sunil.app.presentation.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guru.composecookbook.ui.utils.TestTags
import com.sunil.app.presentation.ui.theme.typography
import com.sunil.custom_snackbar.presentation.ComposeModifiedSnackbar
import com.sunil.custom_snackbar.presentation.ComposeModifiedSnackbarError
import com.sunil.custom_snackbar.presentation.ComposeModifiedSnackbarSuccess
import com.sunil.custom_snackbar.presentation.rememberComposeModifiedSnackbarState
import com.sunil.custom_snackbar.util.ComposeModifiedSnackbarColor
import com.sunil.custom_snackbar.util.ComposeModifiedSnackbarPosition
import com.sunil.custom_snackbar.util.ComposeModifierSnackbarDuration

@Composable
fun HomeScreen() {
    Scaffold(
        modifier = Modifier.testTag(TestTags.WIDGET_SCREEN_ROOT),
        topBar = {
            TopAppBar(
                title = { Text(text = " Home") },
            )
        },
        content = { paddingValues ->
            HomeScreenContent(
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
) {
    val state = rememberComposeModifiedSnackbarState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        val test=""
        Button(onClick = { state.showSnackbar("Snackbar") }) {
            androidx.compose.material.Text(text = "Show Snackbar")
        }
    }
    Text(text = "Home", style = typography.h5, modifier = Modifier.padding(8.dp))
    ComposeModifiedSnackbarSuccess(
        state = state,
        position = ComposeModifiedSnackbarPosition.Bottom,
        duration = ComposeModifierSnackbarDuration.SHORT,
        withDismissAction = true
    )
    ComposeModifiedSnackbarError(
        state = state,
        position = ComposeModifiedSnackbarPosition.Bottom,
        duration = ComposeModifierSnackbarDuration.SHORT,
        withDismissAction = true
    )
    ComposeModifiedSnackbar(
        state = state,
        position = ComposeModifiedSnackbarPosition.Float,
        duration = ComposeModifierSnackbarDuration.INFINITE,
        containerColor = ComposeModifiedSnackbarColor.CustomColor(Color.Black),
        contentColor = ComposeModifiedSnackbarColor.TextWhite,
        withDismissAction = true
    )

}


@Preview
@Composable
fun PreviewScreen() {
    HomeScreen()
}
