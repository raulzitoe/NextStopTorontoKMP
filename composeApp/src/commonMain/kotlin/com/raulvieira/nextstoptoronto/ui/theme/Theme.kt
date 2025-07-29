package com.raulvieira.nextstoptoronto.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun NextStopTorontoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        content = content
    )
}

private val DarkColorScheme = darkColorScheme(
    surface = Color.Black,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    surface = Color.White,
    onSurface = Color.Black
)
