package com.raulvieira.nextstoptoronto.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulvieira.nextstoptoronto.network.httpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    val greeting = MutableStateFlow("")

    init {
        viewModelScope.launch {
            greeting.update { greeting() }
        }
    }

    suspend fun greeting(): String {
        val response = httpClient.get("https://ktor.io/docs/")
        return response.bodyAsText()
    }
}