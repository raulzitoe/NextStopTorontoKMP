package com.raulvieira.nextstoptoronto

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.raulvieira.nextstoptoronto.features.home.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        HomeScreen()
    }
}