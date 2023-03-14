package com.example.tmdbshows.domain.contract

import com.example.tmdbshows.domain.entity.TopRatedEntity
import kotlinx.coroutines.flow.Flow

interface TMDBRepo {
    fun getTopRated(language: String, page: Int): Flow<List<TopRatedEntity>>
}