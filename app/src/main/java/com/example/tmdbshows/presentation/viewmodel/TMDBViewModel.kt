package com.example.tmdbshows.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbshows.domain.contract.TMDBRepo
import com.example.tmdbshows.presentation.uistate.TopRatedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TMDBViewModel @Inject constructor(private val tmdbRepo: TMDBRepo) : ViewModel() {
    private val _topRatedUiStateFlow = MutableStateFlow<TopRatedUiState>(TopRatedUiState.Loading)
    val topRatedUiStateStateFlow: StateFlow<TopRatedUiState> = _topRatedUiStateFlow

    init {
        viewModelScope.launch {
            tmdbRepo.getTopRated("en-US", 1)
                .catch { error ->
                    _topRatedUiStateFlow.value = TopRatedUiState.Error(error)
                }
                .collect { topRatedList ->
                    _topRatedUiStateFlow.value = TopRatedUiState.Success(topRatedList)
                }
        }
    }

    fun sortTopRatedAlphabetically() {
        val currentUiState = _topRatedUiStateFlow.value
        if (currentUiState is TopRatedUiState.Success) {
            val sortedTopRatedList = currentUiState.topRatedList.sortedBy {
                it.name
            }
            _topRatedUiStateFlow.value = TopRatedUiState.Success(sortedTopRatedList)
        }
    }
}