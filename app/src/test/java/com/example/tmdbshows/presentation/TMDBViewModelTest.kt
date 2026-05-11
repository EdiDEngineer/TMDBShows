package com.example.tmdbshows.presentation

import com.example.tmdbshows.domain.contract.TMDBRepo
import com.example.tmdbshows.domain.entity.TopRatedEntity
import com.example.tmdbshows.domain.entity.sortTopRatedListByName
import com.example.tmdbshows.presentation.uistate.UIState
import com.example.tmdbshows.presentation.viewmodel.TMDBViewModel
import com.example.tmdbshows.tools.BaseUnitTest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class) //android mocking
class TMDBViewModelTest : BaseUnitTest() {

    private val throwable: Throwable = Throwable("Network Error")
    @Mock
    private lateinit var tmdbRepo: TMDBRepo
    @Mock
    private lateinit var topRatedEntityList: List<TopRatedEntity>

    @Test
    fun getTopRatedFromRepoTest() = runTest {
        mockSuccessfulCase()
        advanceUntilIdle()
        verify(tmdbRepo, times(1)).getTopRated("en-US", 1)
    }

    @Test
    fun setLoadingStateOnViewModelCreated() = runTest {
        val tmdbViewModel = mockSuccessfulCase()
        assertTrue(tmdbViewModel.topRatedUiStateFlow.value is UIState.Loading)
    }

    @Test
    fun setSuccessStateWhenReceiveTopRatedTest() = runTest {
        val tmdbViewModel = mockSuccessfulCase()
        advanceUntilIdle()
        val uiStateValue = tmdbViewModel.topRatedUiStateFlow.value
        assertTrue(uiStateValue is UIState.Success)
        assertEquals(
            topRatedEntityList, (uiStateValue as UIState.Success).body
        )
    }

    @Test
    fun setSuccessStateWhenSortedAlphabeticallyTest() = runTest {
        val tmdbViewModel = mockSuccessfulCase()
        tmdbViewModel.sortTopRatedAlphabetically()
        advanceUntilIdle()
        val uiStateValue = tmdbViewModel.topRatedUiStateFlow.value
        assertTrue(uiStateValue is UIState.Success)
        assertEquals(
            topRatedEntityList.sortTopRatedListByName(), (uiStateValue as UIState.Success).body
        )
    }

    @Test
    fun setSuccessStateWhenRefreshTopRatedTest() = runTest {
        val tmdbViewModel = mockSuccessfulCase()
        tmdbViewModel.refreshTopRated()
        advanceUntilIdle()
        val uiStateValue = tmdbViewModel.topRatedUiStateFlow.value
        assertTrue(uiStateValue is UIState.Success)
        assertEquals(
            topRatedEntityList, (uiStateValue as UIState.Success).body
        )
    }

    @Test
    fun setErrorStateWhenReceiveErrorTest() = runTest {
        val tmdbViewModel = mockThrowableCase()
        advanceUntilIdle()
        val uiStateValue = tmdbViewModel.topRatedUiStateFlow.value
        assertTrue(uiStateValue is UIState.Failed)
        assertEquals(
            throwable.message,
            (uiStateValue as UIState.Failed).error.message,
        )
    }

    private fun mockSuccessfulCase(): TMDBViewModel {
        whenever(
            tmdbRepo.getTopRated(
                anyString(),
                anyInt(),
            )
        ).thenReturn(flow {
            emit(topRatedEntityList)
        })

        return TMDBViewModel(tmdbRepo)
    }

    private fun mockThrowableCase(): TMDBViewModel {
        whenever(
            tmdbRepo.getTopRated(
                anyString(),
                anyInt(),
            )
        ).thenReturn(flow {
            throw throwable
        })

        return TMDBViewModel(tmdbRepo)
    }
}