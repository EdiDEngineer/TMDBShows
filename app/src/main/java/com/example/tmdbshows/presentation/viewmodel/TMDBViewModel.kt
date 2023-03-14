package com.example.tmdbshows.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbshows.domain.contract.TMDBRepo
import com.example.tmdbshows.domain.impl.TMDBRepoImpl
import com.example.tmdbshows.presentation.uistate.TopRatedUiState
import com.example.tmdbshows.remote.TMDBApi
import com.example.tmdbshows.remote.mapper.toprated.TopRatedNetworkModelMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TMDBViewModel(private val tmdbRepo: TMDBRepo = TMDBRepoImpl(TMDBApi.create(), TopRatedNetworkModelMapper())) : ViewModel() {
    private val _topRatedUiState = MutableStateFlow<TopRatedUiState>(TopRatedUiState.Loading)
    val topRatedUiState: StateFlow<TopRatedUiState> = _topRatedUiState

    init {
        viewModelScope.launch {
            tmdbRepo.getTopRated("en-US", 1)
                .catch { error ->
                    _topRatedUiState.value = TopRatedUiState.Error(error)
                }
                .collect { topRatedList ->
                    _topRatedUiState.value = TopRatedUiState.Success(topRatedList)
                }
        }
    }

    fun sortTopRatedAlphabetically() {
        val currentUiState = _topRatedUiState.value
        if (currentUiState is TopRatedUiState.Success) {
            val sortedTopRatedList = currentUiState.topRatedList.sortedBy {
                it.name
            }
            _topRatedUiState.value = TopRatedUiState.Success(sortedTopRatedList)
        }
    }

}