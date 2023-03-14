package com.example.tmdbshows.presentation.uistate

import com.example.tmdbshows.domain.entity.TopRatedEntity

sealed class TopRatedUiState {
    data class Success(val topRatedList: List<TopRatedEntity>): TopRatedUiState()
    data class Error(val throwable: Throwable): TopRatedUiState()
    object Loading : TopRatedUiState()
}