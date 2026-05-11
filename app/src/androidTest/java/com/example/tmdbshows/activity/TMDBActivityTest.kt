package com.example.tmdbshows.activity

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.tmdbshows.R
import com.example.tmdbshows.tools.BaseUITest
import com.example.tmdbshows.ui.tools.LOADER_TEST_TAG
import com.example.tmdbshows.ui.tools.MOVIE_LIST_TEST_TAG
import com.example.tmdbshows.ui.tools.SORT_BUTTON_TEST_TAG
import com.example.tmdbshows.ui.tools.SORT_ICON_DESCRIPTION
import org.junit.Test

class TMDBActivityTest : BaseUITest() {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun displayHomeScreenWidgetsWhenActivityStarts() {
        composeTestRule.onNode(hasText(composeTestRule.activity.getString(R.string.app_name))).assertIsDisplayed()
        composeTestRule.onNodeWithTag(SORT_BUTTON_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(SORT_ICON_DESCRIPTION).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LOADER_TEST_TAG).assertIsDisplayed()
        composeTestRule.waitUntilDoesNotExist(hasTestTag(LOADER_TEST_TAG))
        composeTestRule.onNodeWithTag(MOVIE_LIST_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag("The D'Amelio Show").assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun displayAlphabeticalListWhenSortButtonIsClicked() {
        composeTestRule.onNodeWithTag(SORT_BUTTON_TEST_TAG).performClick()
        composeTestRule.waitUntilDoesNotExist(hasTestTag(LOADER_TEST_TAG))
        composeTestRule.onNodeWithTag(MOVIE_LIST_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag("The D'Amelio Show").assertIsDisplayed()
    }
}