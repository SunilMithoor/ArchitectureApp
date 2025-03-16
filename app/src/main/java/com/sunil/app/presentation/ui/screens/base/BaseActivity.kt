package com.sunil.app.presentation.ui.screens.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import com.sunil.app.presentation.ui.widget.SnackBar
import com.sunil.custom_snackbar.presentation.ComposeModifiedSnackbar
import com.sunil.custom_snackbar.presentation.ComposeModifiedSnackbarError
import com.sunil.custom_snackbar.presentation.ComposeModifiedSnackbarSuccess
import com.sunil.custom_snackbar.util.ComposeModifiedSnackbarColor
import com.sunil.custom_snackbar.util.ComposeModifiedSnackbarPosition
import com.sunil.custom_snackbar.util.ComposeModifiedSnackbarState
import com.sunil.custom_snackbar.util.ComposeModifierSnackbarDuration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
abstract class BaseActivity : ComponentActivity() {

    companion object {
        private const val TAG = "BaseActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}
