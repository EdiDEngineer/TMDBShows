package com.example.tmdbshows.ui.stateholder

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Stable
class TMDBHomeBodyState @OptIn(ExperimentalMaterialApi::class) constructor(
    val refreshState: PullRefreshState,
    val isRefreshing: Boolean
)

@Stable
class RefreshingState(
    private val coroutineScope: CoroutineScope,
    private val refreshTopRated: () -> Unit
) {

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    fun refresh() = coroutineScope.launch{
        _isRefreshing.value = true
        delay(1500)
        refreshTopRated()
        _isRefreshing.value = false
    }
}

@Composable
fun rememberRefreshingState(refreshTopRated: () -> Unit): RefreshingState {

    val coroutineScope = rememberCoroutineScope()

    return remember(coroutineScope, refreshTopRated) {
        RefreshingState(coroutineScope, refreshTopRated)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberTMDBHomeBodyState(refreshTopRated: () -> Unit): TMDBHomeBodyState {

    val refreshingState = rememberRefreshingState(refreshTopRated)
    val isRefreshing = refreshingState.isRefreshing.value
    val pullRefreshState =
        rememberPullRefreshState(isRefreshing, refreshingState::refresh)

    return remember(pullRefreshState, isRefreshing) {
        TMDBHomeBodyState(pullRefreshState, isRefreshing)
    }
}
