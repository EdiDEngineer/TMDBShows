package com.example.tmdbshows.domain.impl

import com.example.tmdbshows.domain.contract.TMDBRepo
import com.example.tmdbshows.domain.entity.TopRatedEntity
import com.example.tmdbshows.remote.TMDBApi
import com.example.tmdbshows.remote.mapper.toprated.TopRatedNetworkModelMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TMDBRepoImpl(private val tmdbApi: TMDBApi, private val topRatedNetworkModelMapper: TopRatedNetworkModelMapper) : TMDBRepo {

    override fun getTopRated(language: String, page: Int): Flow<List<TopRatedEntity>> {
        return flow {
            val response = tmdbApi.getTopRated(page, language)
            val responseBody = response.body()
            if (responseBody != null) {
                emit(topRatedNetworkModelMapper.mapModelList(responseBody.topRatedNetworkModel))
            } else {
                throw Throwable(response.message())
            }
        }.flowOn(Dispatchers.IO)
    }

}