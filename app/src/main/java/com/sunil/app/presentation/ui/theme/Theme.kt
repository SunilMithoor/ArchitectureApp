package com.sunil.app.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

// dark palettes
//private val DarkGreenColorPalette = darkColors(
//    primary = green200,
//    primaryVariant = green700,
//    secondary = teal200,
//    background = Color.Black,
//    surface = Color.Black,
//    onPrimary = Color.Black,
//    onSecondary = Color.White,
//    onBackground = Color.White,
//    onSurface = Color.White,
//    error = Color.Red,
//)

//private val DarkPurpleColorPalette = darkColors(
//    primary = purple200,
//    primaryVariant = purple700,
//    secondary = teal200,
//    background = Color.Black,
//    surface = Color.Black,
//    onPrimary = Color.Black,
//    onSecondary = Color.White,
//    onBackground = Color.White,
//    onSurface = Color.White,
//    error = Color.Red,
//)

//private val DarkBlueColorPalette = darkColors(
//    primary = blue200,
//    primaryVariant = blue700,
//    secondary = teal200,
//    background = Color.Black,
//    surface = Color.Black,
//    onPrimary = Color.Black,
//    onSecondary = Color.White,
//    onBackground = Color.White,
//    onSurface = Color.White,
//    error = Color.Red,
//)

//private val DarkOrangeColorPalette = darkColors(
//    primary = orange200,
//    primaryVariant = orange700,
//    secondary = teal200,
//    background = Color.Black,
//    surface = Color.Black,
//    onPrimary = Color.Black,
//    onSecondary = Color.White,
//    onBackground = Color.White,
//    onSurface = Color.White,
//    error = Color.Red,
//)

// Light pallets
//private val LightGreenColorPalette = lightColors(
//    primary = green500,
//    primaryVariant = green700,
//    secondary = teal200,
//    background = Color.White,
//    surface = Color.White,
//    onPrimary = Color.White,
//    onSecondary = Color.Black,
//    onBackground = Color.Black,
//    onSurface = Color.Black
//)

//private val LightPurpleColorPalette = lightColors(
//    primary = purple,
//    primaryVariant = purple700,
//    secondary = teal200,
//    background = Color.White,
//    surface = Color.White,
//    onPrimary = Color.White,
//    onSecondary = Color.Black,
//    onBackground = Color.Black,
//    onSurface = Color.Black
//)

//private val LightBlueColorPalette = lightColors(
//    primary = blue500,
//    primaryVariant = blue700,
//    secondary = teal200,
//    background = Color.White,
//    surface = Color.White,
//    onPrimary = Color.White,
//    onSecondary = Color.Black,
//    onBackground = Color.Black,
//    onSurface = Color.Black
//)

//private val LightOrangeColorPalette = lightColors(
//    primary = orange500,
//    primaryVariant = orange700,
//    secondary = teal200,
//    background = Color.White,
//    surface = Color.White,
//    onPrimary = Color.White,
//    onSecondary = Color.Black,
//    onBackground = Color.Black,
//    onSurface = Color.Black
//)


private val darkColors = darkColorScheme(
    primary = Color.Black, // Main Primary Color
    onPrimary = Color.White, // Used for text and icons that appear on top of primary elements.
    primaryContainer = AppColor.RedC1, // Used for elements that contain primary elements, such as floating action buttons.
    background = Color.Black, // Background color used for the app's screens
    surface = Color.Black, // Background color used in components like the NavigationBar and TopAppBar
    onSurface = Color.White, // For example, selected text in NavigationBar
    surfaceVariant = Color.Black, // Used in TextField, SearchView
    onSurfaceVariant = Color.White, // Used for text and icons in NavigationBar and Application Icons
    secondaryContainer = AppColor.RedC1, // Hover color on (NavigationBar)
    onSecondaryContainer = Color.White // Selected icon color on (NavigationBar)
)

private val lightColors = lightColorScheme(
    primary = Color.White, // Main Primary Color
    onPrimary = Color.Black, // Used for text and icons that appear on top of primary elements.
    primaryContainer = AppColor.DarkGrayD3, // Used for elements that contain primary elements, such as floating action buttons.
    background = Color.White, // Background color used for the app's screens
    surface = Color.White, // Background color used in components like the NavigationBar and TopAppBar
//    onSurface = Color.White, // For example, selected text in NavigationBar
    surfaceVariant = Color.White, // Used in TextField, SearchView
//    onSurfaceVariant = Color.White, // Used for text and icons in NavigationBar and Application Icons
    secondaryContainer = AppColor.LightGrayD3, // Hover color on (NavigationBar)
//    onSecondaryContainer = Color.White // Selected icon color on (NavigationBar)
)

enum class ColorPallet {
    PURPLE, GREEN, ORANGE, BLUE, WALLPAPER
}


@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) darkColors else lightColors

    systemUiController.setStatusBarColor(color = colors.primary)

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
