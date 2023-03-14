package com.example.tmdbshows.tools

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tmdbshows.TMDBActivity
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule(TMDBActivity::class.java)
}