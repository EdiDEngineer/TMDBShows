package com.example.tmdbshows.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material.icons.rounded.SortByAlpha
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tmdbshows.R
import com.example.tmdbshows.domain.entity.TopRatedEntity
import com.example.tmdbshows.presentation.uistate.TopRatedUiState

const val SORT_BUTTON_TEST_TAG = "Sort Button"
const val SORT_ICON_DESCRIPTION = "Sort Icon"
const val MOVIE_LIST_TEST_TAG = "Movie list"
const val LOADER_TEST_TAG = "Loader"
const val BROKEN_IMAGE_DESCRIPTION = "Broken Image"
const val MOVIE_IMAGE_DESCRIPTION = "Movie image"

@Composable
fun TMDBHomeScreen(
    modifier: Modifier = Modifier,
    topRatedUiState: TopRatedUiState,
    sortTopRated: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopAppBar(title = {
            Text(stringResource(id = R.string.app_name))
        }, actions = {
            IconButton(onClick = sortTopRated, modifier = modifier.testTag(SORT_BUTTON_TEST_TAG)) {
                Icon(
                    imageVector = Icons.Rounded.SortByAlpha,
                    contentDescription = SORT_ICON_DESCRIPTION
                )
            }
        })
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (topRatedUiState) {
                is TopRatedUiState.Success -> {
                    LazyColumn(modifier = modifier.testTag(MOVIE_LIST_TEST_TAG)) {
                        items(topRatedUiState.topRatedList) { topRatedEntity ->
                            TMDBHomeRow(modifier = modifier, topRatedEntity)
                        }
                    }
                }
                is TopRatedUiState.Loading -> {
                    CircularProgressIndicator(modifier = modifier.testTag(LOADER_TEST_TAG))
                }
                is TopRatedUiState.Error -> {
                    Image(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        imageVector = Icons.Rounded.BrokenImage,
                        contentDescription = BROKEN_IMAGE_DESCRIPTION
                    )
                }
            }
        }
    }
}

@Composable
fun TMDBHomeRow(modifier: Modifier = Modifier, topRatedEntity: TopRatedEntity) {
    Card(
        modifier = modifier
            .testTag(topRatedEntity.name)
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(
                model = topRatedEntity.posterPath,
                contentDescription = MOVIE_IMAGE_DESCRIPTION,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(72.dp)
                    .clip(CircleShape)
            )
            Text(
                modifier = modifier.padding(start = 8.dp),
                text = topRatedEntity.name,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}
