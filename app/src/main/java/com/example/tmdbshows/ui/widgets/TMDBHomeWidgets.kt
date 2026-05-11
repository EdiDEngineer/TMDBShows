package com.example.tmdbshows.ui.widgets

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tmdbshows.domain.entity.TopRatedEntity
import com.example.tmdbshows.ui.tools.MOVIE_IMAGE_DESCRIPTION
import com.example.tmdbshows.ui.tools.MOVIE_LIST_TEST_TAG

@Composable
fun TMDBHomeList(
    modifier: Modifier = Modifier,
    topRatedList: List<TopRatedEntity>,
    isRefreshing: Boolean
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().testTag(MOVIE_LIST_TEST_TAG),
        verticalArrangement = Arrangement.spacedBy(
            (-92).dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isRefreshing) {
            items(topRatedList,
                key = { topRatedEntity -> topRatedEntity.id }
            ) { topRatedEntity ->
                TMDBHomeRow(modifier = modifier, topRatedEntity)
            }
        }
    }
}

@Composable
fun TMDBHomeRow(modifier: Modifier = Modifier, topRatedEntity: TopRatedEntity) {
    Card(
        modifier = modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .testTag(topRatedEntity.name)
            .fillMaxWidth(1.0f)
            .height(200.dp)
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
