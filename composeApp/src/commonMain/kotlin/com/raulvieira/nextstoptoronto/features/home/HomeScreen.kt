package com.raulvieira.nextstoptoronto.features.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = viewModel{ HomeViewModel() }
    val greeting by viewModel.greeting.collectAsStateWithLifecycle()

    Text(
        text = greeting
    )
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
