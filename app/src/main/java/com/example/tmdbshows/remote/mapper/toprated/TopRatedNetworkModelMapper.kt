package com.example.tmdbshows.remote.mapper.toprated

import com.example.tmdbshows.domain.entity.TopRatedEntity
import com.example.tmdbshows.remote.mapper.RemoteModelMapper
import com.example.tmdbshows.remote.model.networkmodel.TopRatedNetworkModel
import javax.inject.Inject

class TopRatedNetworkModelMapper @Inject constructor() :
    RemoteModelMapper<TopRatedNetworkModel, TopRatedEntity> {

    override fun mapFromModel(model: TopRatedNetworkModel): TopRatedEntity {
        return TopRatedEntity(
            safeString(model.backdropPath),
            model.firstAirDate,
            model.genreIds,
            model.id,
            model.name,
            model.originCountry,
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