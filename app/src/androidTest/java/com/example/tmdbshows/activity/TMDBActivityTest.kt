package com.example.tmdbshows.activity

import androidx.compose.ui.test.*
import com.example.tmdbshows.R
import com.example.tmdbshows.tools.BaseUITest
import com.example.tmdbshows.ui.widgets.LOADER_TEST_TAG
import com.example.tmdbshows.ui.widgets.MOVIE_LIST_TEST_TAG
import com.example.tmdbshows.ui.widgets.SORT_BUTTON_TEST_TAG
import com.example.tmdbshows.ui.widgets.SORT_ICON_DESCRIPTION
import org.junit.Test

class TMDBActivityTest : BaseUITest() {

    @Test
    fun displayHomeScreenWidgetsWhenActivityStarts() {
        composeTestRule.onNode(hasText(composeTestRule.activity.getString(R.string.app_name))).assertIsDisplayed()
        composeTestRule.onNodeWithTag(SORT_BUTTON_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(SORT_ICON_DESCRIPTION).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LOADER_TEST_TAG).assertIsDisplayed()
        Thread.sleep(1000)
        composeTestRule.onNodeWithTag(MOVIE_LIST_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag("The D'Amelio Show").assertIsDisplayed()
    }

    @Test
    fun displayAlphabeticalListWhenSortButtonIsClicked() {
        Thread.sleep(1000)
        composeTestRule.onNodeWithTag(SORT_BUTTON_TEST_TAG).performClick()
        composeTestRule.onNodeWithTag(MOVIE_LIST_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag("The D'Amelio Show").assertIsDisplayed()
    }
}