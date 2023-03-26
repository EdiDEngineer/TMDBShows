package com.example.tmdbshows.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tmdbshows.domain.entity.TopRatedEntity
import com.example.tmdbshows.presentation.uistate.UIState
import com.example.tmdbshows.ui.widgets.TMDBHomeAppBar
import com.example.tmdbshows.ui.widgets.TMDBHomeBody

@Composable
fun TMDBHomeScreen(
    modifier: Modifier = Modifier,
    topRatedUiState: UIState<List<TopRatedEntity>>,
    sortTopRated: () -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TMDBHomeAppBar(modifier, sortTopRated)
        }
    ) { paddingValues ->
        TMDBHomeBody(modifier, paddingValues, topRatedUiState)
    }
}