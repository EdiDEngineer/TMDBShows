package com.example.tmdbshows.domain

import com.example.tmdbshows.domain.contract.TMDBRepo
import com.example.tmdbshows.domain.entity.TopRatedEntity
import com.example.tmdbshows.domain.impl.TMDBRepoImpl
import com.example.tmdbshows.remote.TMDBApi
import com.example.tmdbshows.remote.mapper.toprated.TopRatedNetworkModelMapper
import com.example.tmdbshows.remote.model.networkmodel.TopRatedNetworkModel
import com.example.tmdbshows.remote.model.networkresponse.TopRatedNetworkResponse
import org.junit.Test
import com.example.tmdbshows.tools.BaseUnitTest
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import retrofit2.Response

class TMDBRepoTest : BaseUnitTest() {

    private val tmdbApi: TMDBApi = mock()
    private val networkResponse: Response<TopRatedNetworkResponse> = mock()
    private val topRatedNetworkResponse = mock<TopRatedNetworkResponse>()
    private val topRatedNetworkModel = mock<List<TopRatedNetworkModel>>()
    private val topRatedEntityList = mock<List<TopRatedEntity>>()
    private val throwable: Throwable = Throwable("Network Error")
    private val topRatedNetworkModelMapper: TopRatedNetworkModelMapper = mock()

    @Test
    fun getTopRatedFromApiTest() = runTest {
        val tmdbRepo = mockSuccessfulCase()
        launch { tmdbRepo.getTopRated("en-US", 1).first() }
        advanceUntilIdle()
        verify(tmdbApi, times(1)).getTopRated(1, "en-US")
    }

    @Test
    fun mapNetworkResponseToEntityOnSuccessTest() = runTest {
        val tmdbRepo = mockSuccessfulCase()
        launch { tmdbRepo.getTopRated("en-US", 1).first() }
        advanceUntilIdle()
        verify(
            topRatedNetworkModelMapper,
            times(1)
        ).mapModelList(topRatedNetworkModel)
    }

    @Test
    fun emitTopRatedEntityListOnSuccessTest() = runTest {
        val tmdbRepo = mockSuccessfulCase()
        assertEquals(
            topRatedEntityList,
            tmdbRepo.getTopRated("en-US", 1).first()
        )
    }

    @Test
    fun throwErrorOnRequestFailureTest() = runTest {
        val tmdbRepo = mockThrowableCase()
        launch {
            tmdbRepo.getTopRated("en-US", 1).catch {
                assertEquals(
                    throwable.message!!, it.message!!
                )
            }.collect()
        }
    }

    private suspend fun mockThrowableCase(): TMDBRepo {
        whenever(tmdbApi.getTopRated(anyInt(), anyString(), anyString())).thenReturn(
            networkResponse
        )
        whenever(networkResponse.body()).thenReturn(
            null
        )
        whenever(networkResponse.message()).thenReturn(
            throwable.message!!
        )

        return TMDBRepoImpl(tmdbApi, topRatedNetworkModelMapper)
    }

    private suspend fun mockSuccessfulCase(): TMDBRepo {
        whenever(tmdbApi.getTopRated(anyInt(), anyString(), anyString())).thenReturn(
            networkResponse
        )
        whenever(networkResponse.body()).thenReturn(
            topRatedNetworkResponse
        )
        whenever(topRatedNetworkResponse.topRatedNetworkModel).thenReturn(
            topRatedNetworkModel
        )
        whenever(topRatedNetworkModelMapper.mapModelList(networkResponse.body()?.topRatedNetworkModel)).thenReturn(
            topRatedEntityList
        )

        return TMDBRepoImpl(tmdbApi, topRatedNetworkModelMapper)
    }
}