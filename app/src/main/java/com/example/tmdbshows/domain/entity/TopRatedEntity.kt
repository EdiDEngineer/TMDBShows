package com.example.tmdbshows.domain.entity

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class TopRatedEntity(
    val backdropPath: String,
    val firstAirDate: String,
    val genreIds: ImmutableList<Int>,
    val id: Int,
    val name: String,
    val originCountry: ImmutableList<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val voteAverage: Double,
    val voteCount: Int
)

val TopRatedEntity.sortName: String get() = name

fun List<TopRatedEntity>.sortTopRatedListByName() = sortedBy {
    it.sortName
}