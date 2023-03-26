package com.example.tmdbshows.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbshows.domain.contract.TMDBRepo
import com.example.tmdbshows.domain.entity.TopRatedEntity
import com.example.tmdbshows.presentation.uistate.Transform.SORT_ALPHABETICALLY
import com.example.tmdbshows.presentation.uistate.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TMDBViewModel @Inject constructor(private val tmdbRepo: TMDBRepo) : ViewModel() {
    private val _topRatedUiStateFlow = MutableStateFlow<UIState<List<TopRatedEntity>>>(UIState.Loading)
    val topRatedUiStateFlow: StateFlow<UIState<List<TopRatedEntity>>> = _topRatedUiStateFlow

    init {
        viewModelScope.launch {
            tmdbRepo.getTopRated("en-US", 1)
                .catch { error ->
                    _topRatedUiStateFlow.value = UIState.Failed(error)
                }
                .collect { topRatedList ->
                    _topRatedUiStateFlow.value = UIState.Success(topRatedList)
                }
        }
    }

    fun sortTopRatedAlphabetically() {
        val currentUiState = _topRatedUiStateFlow.value
        if (currentUiState is UIState.Success && currentUiState.transform != SORT_ALPHABETICALLY) {
            val sortedTopRatedList = currentUiState.body.sortedBy {
                it.name
            }
            _topRatedUiStateFlow.value = UIState.Success(currentUiState.body, sortedTopRatedList, SORT_ALPHABETICALLY)
        }
    }
}