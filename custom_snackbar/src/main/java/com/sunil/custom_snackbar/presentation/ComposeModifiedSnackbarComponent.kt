package com.sunil.custom_snackbar.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sunil.custom_snackbar.util.ComposeModifiedSnackbarColor
import com.sunil.custom_snackbar.util.ComposeModifiedSnackbarPosition
import com.sunil.custom_snackbar.util.ComposeModifiedSnackbarState
import com.sunil.custom_snackbar.util.ComposeModifierSnackbarDuration
import java.util.Timer
import kotlin.concurrent.schedule

@Composable
internal fun ComposeModifiedSnackbarComponent(
    state: ComposeModifiedSnackbarState,
    duration: ComposeModifierSnackbarDuration,
    position: ComposeModifiedSnackbarPosition,
    containerColor: ComposeModifiedSnackbarColor,
    contentColor: ComposeModifiedSnackbarColor,
    verticalPadding: Dp,
    horizontalPadding: Dp,
    icon: ImageVector?,
    enterAnimation: EnterTransition,
    exitAnimation: ExitTransition,
    withDismissAction: Boolean
) {
    var showSnackbar by remember { mutableStateOf(false) }
    val message by rememberUpdatedState(newValue = state.message.value)
    val timer = Timer("Animation Timer", true)

    DisposableEffect(
        key1 = state.updateState
    ) {
        showSnackbar = true
        timer.schedule(duration.time) {
            showSnackbar = false
        }
        onDispose {
            timer.cancel()
            timer.purge()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = when (position) {
                    is ComposeModifiedSnackbarPosition.Top -> 0.dp
                    is ComposeModifiedSnackbarPosition.Bottom -> 0.dp
                    is ComposeModifiedSnackbarPosition.Float -> 24.dp
                }
            ),
        verticalArrangement = when (position) {
            is ComposeModifiedSnackbarPosition.Top -> Arrangement.Top
            is ComposeModifiedSnackbarPosition.Bottom -> Arrangement.Bottom
            is ComposeModifiedSnackbarPosition.Float -> Arrangement.Bottom
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = state.isNotEmpty() && showSnackbar,
            enter = when (position) {
                is ComposeModifiedSnackbarPosition.Top -> enterAnimation
                is ComposeModifiedSnackbarPosition.Bottom -> enterAnimation
                is ComposeModifiedSnackbarPosition.Float -> fadeIn()
            },
            exit = when (position) {
                is ComposeModifiedSnackbarPosition.Top -> exitAnimation
                is ComposeModifiedSnackbarPosition.Bottom -> exitAnimation
                is ComposeModifiedSnackbarPosition.Float -> fadeOut()
            }
        ) {
            ModifiedSnackbar(
                message = message,
                position = position,
                containerColor = containerColor,
                contentColor = contentColor,
                verticalPadding = verticalPadding,
                horizontalPadding = horizontalPadding,
                icon = icon,
                withDismissAction = withDismissAction,
                onDismiss = {
                    if (withDismissAction) {
                        showSnackbar = false
                        timer.cancel()
                        timer.purge()
                    }
                }
            )
        }
    }
}
