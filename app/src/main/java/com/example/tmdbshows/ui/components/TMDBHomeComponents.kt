package com.example.tmdbshows.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material.icons.rounded.SortByAlpha
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tmdbshows.R
import com.example.tmdbshows.domain.entity.TopRatedEntity
import com.example.tmdbshows.presentation.uistate.UIState
import com.example.tmdbshows.ui.stateholder.TMDBHomeBodyState
import com.example.tmdbshows.ui.theme.TMDBShowsTheme
import com.example.tmdbshows.ui.tools.BROKEN_IMAGE_DESCRIPTION
import com.example.tmdbshows.ui.tools.LOADER_TEST_TAG
import com.example.tmdbshows.ui.tools.SORT_BUTTON_TEST_TAG
import com.example.tmdbshows.ui.tools.SORT_ICON_DESCRIPTION
import com.example.tmdbshows.ui.widgets.TMDBHomeList

@Composable
fun TMDBHomeAppBar(modifier: Modifier = Modifier, sortTopRated: () -> Unit) {
    TopAppBar(title = {
        Text(stringResource(id = R.string.app_name))
    }, actions = {
        IconButton(
            onClick = sortTopRated,
            modifier = modifier.testTag(SORT_BUTTON_TEST_TAG)
        ) {
            Icon(
                imageVector = Icons.Rounded.SortByAlpha,
                contentDescription = SORT_ICON_DESCRIPTION
            )
        }
    })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TMDBHomeBody(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    topRatedUiState: UIState<List<TopRatedEntity>>,
    tmdbHomeBodyState: TMDBHomeBodyState,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        when (topRatedUiState) {
            is UIState.Success -> {
                Box(modifier.pullRefresh(tmdbHomeBodyState.refreshState)) {
                    TMDBHomeList(modifier, topRatedUiState.body, tmdbHomeBodyState.isRefreshing)
                    PullRefreshIndicator(
                        tmdbHomeBodyState.isRefreshing,
                        tmdbHomeBodyState.refreshState,
                        modifier.align(Alignment.TopCenter)
                    )
                }
            }

            is UIState.Loading -> {
                CircularProgressIndicator(modifier = modifier.testTag(LOADER_TEST_TAG))
            }

            is UIState.Failed -> {
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

@Composable
private fun animateCenterAlignmentAsState(
    targetBiasValue: Float
): State<BiasAlignment> {
    val bias by animateFloatAsState(targetBiasValue)
    return remember { derivedStateOf { BiasAlignment(bias, 0f) } }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TextFieldWithIcons() {
    TMDBShowsTheme {

    }
}

//internal object DashboardScreen : HomeNavScreen {
//    override val drawerIcon: Int
//        get() = R.drawable.ic_dashboard
//    override val title: String
//        get() = _title
//    override val drawerTitle: String
//        get() = "Dashboard"
//    private var _title = ""
//
//    fun setTitle(newTitle: String): DashboardScreen {
//        _title = newTitle
//        return this
//    }
//    const val accountTypeArg = "account_type"
//
//    val routeWithArgs = "$route/{$accountTypeArg}"
//    val arguments = listOf(
//        navArgument(accountTypeArg) { type = NavType.StringType }
//    )
//    composable(
//    route = SingleAccount.routeWithArgs,
//    arguments = SingleAccount.arguments,
//    ) { navBackStackEntry ->
//        val accountType =
//            navBackStackEntry.arguments?.getString(SingleAccount.accountTypeArg)
//}