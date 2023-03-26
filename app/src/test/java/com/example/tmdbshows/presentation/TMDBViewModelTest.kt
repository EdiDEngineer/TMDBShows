package com.example.tmdbshows.presentation

import com.example.tmdbshows.domain.contract.TMDBRepo
import com.example.tmdbshows.domain.entity.TopRatedEntity
import com.example.tmdbshows.presentation.uistate.Transform.DEFAULT
import com.example.tmdbshows.presentation.uistate.Transform.SORT_ALPHABETICALLY
import com.example.tmdbshows.presentation.uistate.UIState
import com.example.tmdbshows.presentation.viewmodel.TMDBViewModel
import com.example.tmdbshows.tools.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString

class TMDBViewModelTest : BaseUnitTest() {

    private val tmdbRepo: TMDBRepo = mock()
    private val topRatedEntityList = mock<List<TopRatedEntity>>()
    private val throwable: Throwable = Throwable("Network Error")

    @Test
    fun getTopRatedFromRepoTest() = runTest {
        mockSuccessfulCase()
        advanceUntilIdle()
        verify(tmdbRepo, times(1)).getTopRated("en-US", 1)
    }

    @Test
    fun setSuccessStateWhenReceiveTopRatedTest() = runTest {
        val tmdbViewModel = mockSuccessfulCase()
        var uiStateValue = tmdbViewModel.topRatedUiStateFlow.value
        assertTrue(uiStateValue is UIState.Loading)
        advanceUntilIdle()
        uiStateValue = tmdbViewModel.topRatedUiStateFlow.value
        assertTrue(uiStateValue is UIState.Success && uiStateValue.transform == DEFAULT)
        assertEquals(
            (uiStateValue as UIState.Success).displayBody,
            topRatedEntityList
        )
    }

    @Test
    fun setSortedStateWhenSortedAlphabeticallyTest() = runTest {
        val tmdbViewModel = mockSuccessfulCase()
        var uiStateValue = tmdbViewModel.topRatedUiStateFlow.value
        assertTrue(uiStateValue is UIState.Loading)
        advanceUntilIdle()
        tmdbViewModel.sortTopRatedAlphabetically()
        uiStateValue = tmdbViewModel.topRatedUiStateFlow.value
        assertTrue(uiStateValue is UIState.Success && uiStateValue.transform == SORT_ALPHABETICALLY)
        assertEquals((uiStateValue as UIState.Success).displayBody,
            topRatedEntityList.sortedBy {
                it.name
            }
        )
    }

    @Test
    fun setErrorStateWhenReceiveErrorTest() = runTest {
        val tmdbViewModel = mockThrowableCase()
        var uiStateValue = tmdbViewModel.topRatedUiStateFlow.value
        assertTrue(uiStateValue is UIState.Loading)
        advanceUntilIdle()
        uiStateValue = tmdbViewModel.topRatedUiStateFlow.value
        assertTrue(uiStateValue is UIState.Failed)
        assertEquals(
            (uiStateValue as UIState.Failed).error,
            throwable
        )
    }

    private fun mockSuccessfulCase(): TMDBViewModel {
        whenever(
            tmdbRepo.getTopRated(
                anyString(),
                anyInt(),
            )
        ).thenReturn(
            flow {
                emit(topRatedEntityList)
            }
        )

        return TMDBViewModel(tmdbRepo)
    }

    private fun mockThrowableCase(): TMDBViewModel {
        whenever(
            tmdbRepo.getTopRated(
                anyString(),
                anyInt(),
            )
        ).thenReturn(
            flow {
                throw throwable
            }
        )

        return TMDBViewModel(tmdbRepo)
    }
}