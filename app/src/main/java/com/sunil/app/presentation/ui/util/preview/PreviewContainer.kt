package com.sunil.app.presentation.ui.util.preview

import androidx.compose.runtime.Composable
import com.sunil.app.presentation.ui.theme.AppTheme


@Composable
fun PreviewContainer(
    content: @Composable () -> Unit
) {
    AppTheme {
        content()
    }
}
