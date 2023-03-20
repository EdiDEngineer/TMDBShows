package com.example.tmdbshows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tmdbshows.presentation.viewmodel.TMDBViewModel
import com.example.tmdbshows.ui.screen.TMDBHomeScreen
import com.example.tmdbshows.ui.theme.TMDBShowsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TMDBActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tmdbViewModel: TMDBViewModel by viewModels()
        setContent {
            TMDBHomeApp(tmdbViewModel)
        }
    }
}

@Composable
fun TMDBHomeApp(tmdbViewModel: TMDBViewModel = viewModel()) {
    val topRatedUiState = tmdbViewModel.topRatedUiStateStateFlow.collectAsStateWithLifecycle().value
    TMDBShowsTheme {
        TMDBHomeScreen(topRatedUiState = topRatedUiState, sortTopRated = {
            tmdbViewModel.sortTopRatedAlphabetically()
        })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TMDBHomeApp()
}