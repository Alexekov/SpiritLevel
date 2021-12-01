package com.alexeykov.spiritlevel.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*

private val DarkColorPalette = darkColors()

private val LightColorPalette = lightColors(
    primary = Blue500,
    primaryVariant = Blue700,
    secondary = Blue200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

object ThemeState {
    var isLight by mutableStateOf(true)
}

@Composable
fun SpiritLevelTheme(
    content: @Composable () -> Unit
) {
    if (isSystemInDarkTheme()) ThemeState.isLight = false
    if (!ThemeState.isLight) {
        MaterialTheme(colors = DarkColorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    } else {
        MaterialTheme(colors = LightColorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}