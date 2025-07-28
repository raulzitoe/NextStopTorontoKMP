package com.raulvieira.nextstoptoronto.ui.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen() {
    val viewModel = koinViewModel<HomeViewModel>()
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is HomeUiSate.Error -> Text(state.message)
        is HomeUiSate.Loading -> Text("Loading")
        is HomeUiSate.Success -> {
            LazyColumn {
                item {
                    Text("Lines")
                }
                items(state.lines) { route ->
                    Text(route.title)
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
