package com.raulvieira.nextstoptoronto.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulvieira.nextstoptoronto.network.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository
): ViewModel() {
    private val _state: MutableStateFlow<HomeUiSate> = MutableStateFlow(HomeUiSate.Loading)
    val state = _state.asStateFlow()

    init {
        initializeScreenState()
    }

    private fun initializeScreenState() {
        viewModelScope.launch {
            repository.getRouteList().onSuccess { response ->
                _state.update { HomeUiSate.Success(response.toDomain()) }
            }.onFailure { throwable ->
                _state.update { HomeUiSate.Error(throwable.message ?: "Unknown error") }
            }
        }
    }

    fun onRefresh() {
        if (state.value !is HomeUiSate.Success) initializeScreenState()
    }
}
