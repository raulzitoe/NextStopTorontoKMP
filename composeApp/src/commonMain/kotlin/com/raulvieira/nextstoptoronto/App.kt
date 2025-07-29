package com.raulvieira.nextstoptoronto

import androidx.compose.runtime.Composable
import com.raulvieira.nextstoptoronto.di.koinModule
import com.raulvieira.nextstoptoronto.ui.home.HomeScreen
import com.raulvieira.nextstoptoronto.ui.theme.NextStopTorontoTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    NextStopTorontoTheme {
        KoinApplication(
            application = {
                modules(koinModule)
            }
        ) {
            HomeScreen(
                onNavigate = {}
            )
        }
    }
}
