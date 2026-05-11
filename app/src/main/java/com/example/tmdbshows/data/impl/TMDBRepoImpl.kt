package com.example.tmdbshows.data.impl

import com.example.tmdbshows.data.remote.api.TMDBService
import com.example.tmdbshows.data.remote.mapper.toprated.TopRatedNetworkModelMapper
import com.example.tmdbshows.di.IODispatcher
import com.example.tmdbshows.domain.contract.TMDBRepo
import com.example.tmdbshows.domain.entity.TopRatedEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TMDBRepoImpl @Inject constructor(
    private val tmdbService: TMDBService,
    private val topRatedNetworkModelMapper: TopRatedNetworkModelMapper,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher
) : TMDBRepo {

    override fun getTopRated(language: String, page: Int): Flow<List<TopRatedEntity>> {
        return flow {
            val response = tmdbService.getTopRated(page, language)
            val responseBody = response.body()
            if (responseBody != null) {
                emit(topRatedNetworkModelMapper.mapModelList(responseBody.topRatedNetworkModel))
            } else {
                throw Throwable(response.message())
            }
        }.flowOn(coroutineDispatcher)
    }
}