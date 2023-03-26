package com.example.tmdbshows.activity

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import com.example.tmdbshows.R
import com.example.tmdbshows.tools.BaseUITest
import com.example.tmdbshows.ui.screen.LOADER_TEST_TAG
import com.example.tmdbshows.ui.screen.MOVIE_LIST_TEST_TAG
import com.example.tmdbshows.ui.screen.SORT_BUTTON_TEST_TAG
import com.example.tmdbshows.ui.screen.SORT_ICON_DESCRIPTION
import org.junit.Test

class TMDBActivityTest : BaseUITest() {

    @Test
    fun tmdbTopAppBarText_IsDisplayed() {
        composeTestRule.onNode(hasText(composeTestRule.activity.getString(R.string.app_name))).assertIsDisplayed()
    }

    @Test
    fun sortIcon_IsDisplayed() {
        composeTestRule.onNodeWithContentDescription(SORT_ICON_DESCRIPTION).assertIsDisplayed()
    }

    @Test
    fun sortButtonAndLoader_IsDisplayed() {
        composeTestRule.onNodeWithTag(LOADER_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(SORT_BUTTON_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun movieList_IsDisplayed() {
        Thread.sleep(1000)
        composeTestRule.onNodeWithTag(MOVIE_LIST_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun movie_IsDisplayed() {
        Thread.sleep(1000)
        composeTestRule.onNodeWithTag("The D'Amelio Show").assertIsDisplayed()
    }
}