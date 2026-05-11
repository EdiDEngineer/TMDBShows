package com.example.tmdbshows.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tmdbshows.domain.entity.TopRatedEntity
import com.example.tmdbshows.presentation.uistate.UIState
import com.example.tmdbshows.ui.components.TMDBHomeAppBar
import com.example.tmdbshows.ui.components.TMDBHomeBody
import com.example.tmdbshows.ui.stateholder.rememberTMDBHomeBodyState

@Composable
fun TMDBHomeScreen(
    modifier: Modifier = Modifier,
    topRatedUiState: UIState<List<TopRatedEntity>>,
    sortTopRated: () -> Unit,
    refreshTopRated: () -> Unit
) {

    val tmdbHomeBodyState = rememberTMDBHomeBodyState(refreshTopRated)

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TMDBHomeAppBar(modifier, sortTopRated)
        }
    ) { paddingValues ->
        TMDBHomeBody(
            modifier,
            paddingValues,
            topRatedUiState,
            tmdbHomeBodyState
        )
    }
}
