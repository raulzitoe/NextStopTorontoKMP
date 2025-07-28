package com.raulvieira.nextstoptoronto

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.raulvieira.nextstoptoronto.di.koinModule
import com.raulvieira.nextstoptoronto.ui.home.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinApplication(
            application = {
                modules(koinModule)
            }
        ) {
            HomeScreen()
        }
    }
}
