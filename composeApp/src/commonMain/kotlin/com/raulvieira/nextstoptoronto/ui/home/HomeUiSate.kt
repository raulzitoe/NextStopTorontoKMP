package com.raulvieira.nextstoptoronto.ui.home

import com.raulvieira.nextstoptoronto.domain.model.Line

sealed class HomeUiSate {
    object Loading : HomeUiSate()
    data class Success(
        val lines: List<Line>
    ) : HomeUiSate()
    data class Error(val message: String) : HomeUiSate()
}