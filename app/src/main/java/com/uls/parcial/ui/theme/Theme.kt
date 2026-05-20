package com.uls.parcial.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary   = primary,
    secondary = secondary,
    tertiary  = tertiary,
    background = DarkBackGround,
    surface    = surface,
    onPrimary  = onPrimary,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun ParcialTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}