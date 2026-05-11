package com.example.tmdbshows.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbshows.domain.contract.TMDBRepo
import com.example.tmdbshows.domain.entity.TopRatedEntity
import com.example.tmdbshows.domain.entity.sortTopRatedListByName
import com.example.tmdbshows.presentation.uistate.SuccessTransform
import com.example.tmdbshows.presentation.uistate.SuccessTransform.DEFAULT
import com.example.tmdbshows.presentation.uistate.SuccessTransform.SORT_ALPHABETICALLY
import com.example.tmdbshows.presentation.uistate.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TMDBViewModel @Inject constructor(private val tmdbRepo: TMDBRepo) : ViewModel() {
    private val _topRatedUiStateFlow =
        MutableStateFlow<UIState<List<TopRatedEntity>>>(UIState.Loading)
    val topRatedUiStateFlow: StateFlow<UIState<List<TopRatedEntity>>> =
        _topRatedUiStateFlow.asStateFlow()
    private val _successTransformFlow = MutableStateFlow(DEFAULT)

    init {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {
            tmdbRepo.getTopRated("en-US", 1)
                .combine(_successTransformFlow) { topRatedList, transform ->
                    getTransformedList(topRatedList, transform)
                }.catch { error ->
                    _topRatedUiStateFlow.update { UIState.Failed(error) }
                }.collectLatest { topRatedList ->
                    _topRatedUiStateFlow.update { UIState.Success(topRatedList) }
                }
        }
    }

    private fun getTransformedList(
        topRatedList: List<TopRatedEntity>,
        successTransform: SuccessTransform
    ): List<TopRatedEntity> =
        when (successTransform) {
            SORT_ALPHABETICALLY -> topRatedList.sortTopRatedListByName()
            else -> topRatedList
        }

    fun sortTopRatedAlphabetically() {
        _successTransformFlow.update { SORT_ALPHABETICALLY }
    }

    fun refreshTopRated() {
        _successTransformFlow.update { DEFAULT } // atomicity
    }
}