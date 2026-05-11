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
import com.example.tmdbshows.ui.screens.TMDBHomeScreen
import com.example.tmdbshows.ui.theme.TMDBShowsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TMDBActivity : ComponentActivity() {
    private val tmdbViewModel: TMDBViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TMDBHomeApp(tmdbViewModel)
        }

        /**
         *  viewLifecycleOwner.lifecycleScope.launch {
         *         viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
         *             viewModel.uiState.collect {
         *                 // Process item
         *             }
         *         }
         *     }
         * **/
    }



}

@Composable
fun TMDBHomeApp(tmdbViewModel: TMDBViewModel = viewModel()) {
    val topRatedUiState = tmdbViewModel.topRatedUiStateFlow.collectAsStateWithLifecycle().value
    TMDBShowsTheme {
        TMDBHomeScreen(topRatedUiState = topRatedUiState, sortTopRated = {
            tmdbViewModel.sortTopRatedAlphabetically()
        }, refreshTopRated = {
            tmdbViewModel.refreshTopRated()
        })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TMDBHomeApp()
}