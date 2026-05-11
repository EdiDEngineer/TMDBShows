package com.example.tmdbshows.data.remote.mapper.toprated

import com.example.tmdbshows.domain.entity.TopRatedEntity
import com.example.tmdbshows.data.remote.mapper.NetworkModelMapper
import com.example.tmdbshows.data.remote.model.networkmodel.TopRatedNetworkModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

class TopRatedNetworkModelMapper @Inject constructor() :
    NetworkModelMapper<TopRatedNetworkModel, TopRatedEntity> {

    override fun mapFromModel(model: TopRatedNetworkModel): TopRatedEntity {
        return TopRatedEntity(
            safeString(model.backdropPath),
            model.firstAirDate,
            model.genreIds.toImmutableList(),
            model.id,
            model.name,
            model.originCountry.toImmutableList(),
            model.originalLanguage,
            model.originalName,
            model.overview,
            model.popularity,
            safeString(model.posterPath, POSTER_PATH_BASE_URL),
            model.voteAverage,
            model.voteCount
        )
    }

    companion object {
        const val POSTER_PATH_BASE_URL = "https://image.tmdb.org/t/p/w500/"
    }
}