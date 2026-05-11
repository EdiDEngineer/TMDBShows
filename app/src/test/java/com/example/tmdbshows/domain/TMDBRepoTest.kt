package com.example.tmdbshows.domain

import com.example.tmdbshows.domain.contract.TMDBRepo
import com.example.tmdbshows.domain.entity.TopRatedEntity
import com.example.tmdbshows.data.impl.TMDBRepoImpl
import com.example.tmdbshows.data.remote.api.TMDBService
import com.example.tmdbshows.data.remote.mapper.toprated.TopRatedNetworkModelMapper
import com.example.tmdbshows.data.remote.model.networkmodel.TopRatedNetworkModel
import com.example.tmdbshows.data.remote.model.networkresponse.TopRatedNetworkResponse
import org.junit.Test
import com.example.tmdbshows.tools.BaseUnitTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class TMDBRepoTest : BaseUnitTest() {

    private val throwableMessage = "Network Error"
    @Mock
    private lateinit var tmdbService: TMDBService
    @Mock
    private lateinit var networkResponse: Response<TopRatedNetworkResponse>
    @Mock
    private lateinit var topRatedNetworkResponse:TopRatedNetworkResponse
    @Mock
    private lateinit var topRatedNetworkModel: List<TopRatedNetworkModel>
    @Mock
    private lateinit var topRatedEntityList:List<TopRatedEntity>
    @Mock
    private lateinit var topRatedNetworkModelMapper: TopRatedNetworkModelMapper

    @Test
    fun getTopRatedFromApiTest() = runTest {
        val tmdbRepo = mockSuccessfulCase()
        launch { tmdbRepo.getTopRated("en-US", 1).first() }
        advanceUntilIdle()
        verify(tmdbService, times(1)).getTopRated(1, "en-US")
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
                    throwableMessage, it.message!!
                )
            }.collect()
        }
    }

    private suspend fun mockThrowableCase(): TMDBRepo {
        whenever(tmdbService.getTopRated(anyInt(), anyString())).thenReturn(
            networkResponse
        )
        whenever(networkResponse.body()).thenReturn(
            null
        )
        whenever(networkResponse.message()).thenReturn(
            throwableMessage
        )

        return TMDBRepoImpl(tmdbService, topRatedNetworkModelMapper, Dispatchers.Unconfined)
    }

    private suspend fun mockSuccessfulCase(): TMDBRepo {
        whenever(tmdbService.getTopRated(anyInt(), anyString())).thenReturn(
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

        return TMDBRepoImpl(tmdbService, topRatedNetworkModelMapper, Dispatchers.Unconfined)
    }
}