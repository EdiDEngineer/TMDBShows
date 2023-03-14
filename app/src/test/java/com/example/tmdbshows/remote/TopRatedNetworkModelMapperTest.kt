package com.example.tmdbshows.remote

import com.example.tmdbshows.domain.entity.TopRatedEntity
import com.example.tmdbshows.remote.mapper.toprated.TopRatedNetworkModelMapper
import com.example.tmdbshows.remote.model.networkmodel.TopRatedNetworkModel
import com.example.tmdbshows.tools.BaseUnitTest
import junit.framework.TestCase.assertEquals
import org.junit.Test

class TopRatedNetworkModelMapperTest : BaseUnitTest() {

    private val classUnderTest = TopRatedNetworkModelMapper()

    @Test
    fun modelWithBackdropAndPosterMappedToEntityTest() {
        val topRatedNetworkModel = createFakeTopRatedNetworkModel(
            "/99vBORZixICa32Pwdwj0lWcr8K.jpg",
            "/phv2Jc4H8cvRzvTKb9X1uKMboTu.jpg"
        )

        val topRatedEntity = classUnderTest.mapFromModel(topRatedNetworkModel)
        validateMappedBackdropPath(topRatedNetworkModel, topRatedEntity)
        validateMappedFirstAirDate(topRatedNetworkModel, topRatedEntity)
        validateMappedGenreIds(topRatedNetworkModel, topRatedEntity)
        validateMappedId(topRatedNetworkModel, topRatedEntity)
        validateMappedName(topRatedNetworkModel, topRatedEntity)
        validateMappedOriginCountry(topRatedNetworkModel, topRatedEntity)
        validateMappedOriginLanguage(topRatedNetworkModel, topRatedEntity)
        validateMappedOriginName(topRatedNetworkModel, topRatedEntity)
        validateMappedOverview(topRatedNetworkModel, topRatedEntity)
        validateMappedPopularity(topRatedNetworkModel, topRatedEntity)
        validateMappedPosterPath(topRatedNetworkModel, topRatedEntity)
        validateMappedVoteAverage(topRatedNetworkModel, topRatedEntity)
        validateMappedVoteCount(topRatedNetworkModel, topRatedEntity)
    }

    @Test
    fun modelWithoutBackdropAndPosterMappedToEntityTest() {
        val topRatedNetworkModel = createFakeTopRatedNetworkModel(
            null,
            null
        )

        val topRatedEntity = classUnderTest.mapFromModel(topRatedNetworkModel)
        validateMappedBackdropPath(topRatedNetworkModel, topRatedEntity)
        validateMappedFirstAirDate(topRatedNetworkModel, topRatedEntity)
        validateMappedGenreIds(topRatedNetworkModel, topRatedEntity)
        validateMappedId(topRatedNetworkModel, topRatedEntity)
        validateMappedName(topRatedNetworkModel, topRatedEntity)
        validateMappedOriginCountry(topRatedNetworkModel, topRatedEntity)
        validateMappedOriginLanguage(topRatedNetworkModel, topRatedEntity)
        validateMappedOriginName(topRatedNetworkModel, topRatedEntity)
        validateMappedOverview(topRatedNetworkModel, topRatedEntity)
        validateMappedPopularity(topRatedNetworkModel, topRatedEntity)
        validateMappedPosterPath(topRatedNetworkModel, topRatedEntity)
        validateMappedVoteAverage(topRatedNetworkModel, topRatedEntity)
        validateMappedVoteCount(topRatedNetworkModel, topRatedEntity)
    }

    @Test
    fun modelListMappedToEntityListTest() {
        val topRatedNetworkModelList = listOf(
            createFakeTopRatedNetworkModel(
                null,
                null
            )
        )

        val topRatedEntityList = classUnderTest.mapModelList(topRatedNetworkModelList)
        val topRatedNetworkModel = topRatedNetworkModelList[0]
        val topRatedEntity = topRatedEntityList[0]

        assertEquals(topRatedNetworkModelList.size, topRatedEntityList.size)

        validateMappedBackdropPath(topRatedNetworkModel, topRatedEntity)
        validateMappedFirstAirDate(topRatedNetworkModel, topRatedEntity)
        validateMappedGenreIds(topRatedNetworkModel, topRatedEntity)
        validateMappedId(topRatedNetworkModel, topRatedEntity)
        validateMappedName(topRatedNetworkModel, topRatedEntity)
        validateMappedOriginCountry(topRatedNetworkModel, topRatedEntity)
        validateMappedOriginLanguage(topRatedNetworkModel, topRatedEntity)
        validateMappedOriginName(topRatedNetworkModel, topRatedEntity)
        validateMappedOverview(topRatedNetworkModel, topRatedEntity)
        validateMappedPopularity(topRatedNetworkModel, topRatedEntity)
        validateMappedPosterPath(topRatedNetworkModel, topRatedEntity)
        validateMappedVoteAverage(topRatedNetworkModel, topRatedEntity)
        validateMappedVoteCount(topRatedNetworkModel, topRatedEntity)

    }

    private fun validateMappedBackdropPath(
        topRatedNetworkModel: TopRatedNetworkModel,
        topRatedEntity: TopRatedEntity
    ) {
        assertEquals(
            if (topRatedNetworkModel.backdropPath == null) "N/A" else
                topRatedNetworkModel.backdropPath,
            topRatedEntity.backdropPath
        )
    }

    private fun validateMappedFirstAirDate(
        topRatedNetworkModel: TopRatedNetworkModel,
        topRatedEntity: TopRatedEntity
    ) {
        assertEquals(topRatedNetworkModel.firstAirDate, topRatedEntity.firstAirDate)
    }

    private fun validateMappedGenreIds(
        topRatedNetworkModel: TopRatedNetworkModel,
        topRatedEntity: TopRatedEntity
    ) {
        assertEquals(topRatedNetworkModel.genreIds, topRatedEntity.genreIds)
    }

    private fun validateMappedId(
        topRatedNetworkModel: TopRatedNetworkModel,
        topRatedEntity: TopRatedEntity
    ) {
        assertEquals(topRatedNetworkModel.id, topRatedEntity.id)
    }

    private fun validateMappedName(
        topRatedNetworkModel: TopRatedNetworkModel,
        topRatedEntity: TopRatedEntity
    ) {
        assertEquals(topRatedNetworkModel.name, topRatedEntity.name)
    }

    private fun validateMappedOriginCountry(
        topRatedNetworkModel: TopRatedNetworkModel,
        topRatedEntity: TopRatedEntity
    ) {
        assertEquals(topRatedNetworkModel.originCountry, topRatedEntity.originCountry)
    }

    private fun validateMappedOriginLanguage(
        topRatedNetworkModel: TopRatedNetworkModel,
        topRatedEntity: TopRatedEntity
    ) {
        assertEquals(topRatedNetworkModel.originalLanguage, topRatedEntity.originalLanguage)
    }

    private fun validateMappedOriginName(
        topRatedNetworkModel: TopRatedNetworkModel,
        topRatedEntity: TopRatedEntity
    ) {
        assertEquals(topRatedNetworkModel.originalName, topRatedEntity.originalName)
    }

    private fun validateMappedOverview(
        topRatedNetworkModel: TopRatedNetworkModel,
        topRatedEntity: TopRatedEntity
    ) {
        assertEquals(topRatedNetworkModel.overview, topRatedEntity.overview)
    }

    private fun validateMappedPopularity(
        topRatedNetworkModel: TopRatedNetworkModel,
        topRatedEntity: TopRatedEntity
    ) {
        assertEquals(topRatedNetworkModel.popularity, topRatedEntity.popularity)
    }

    private fun validateMappedPosterPath(
        topRatedNetworkModel: TopRatedNetworkModel,
        topRatedEntity: TopRatedEntity
    ) {
        assertEquals(
            if (topRatedNetworkModel.posterPath == null) "N/A" else TopRatedNetworkModelMapper.POSTER_PATH_BASE_URL +
                    topRatedNetworkModel.posterPath,
            topRatedEntity.posterPath
        )
    }

    private fun validateMappedVoteAverage(
        topRatedNetworkModel: TopRatedNetworkModel,
        topRatedEntity: TopRatedEntity
    ) {
        assertEquals(topRatedNetworkModel.voteAverage, topRatedEntity.voteAverage)
    }

    private fun validateMappedVoteCount(
        topRatedNetworkModel: TopRatedNetworkModel,
        topRatedEntity: TopRatedEntity
    ) {
        assertEquals(topRatedNetworkModel.voteCount, topRatedEntity.voteCount)
    }

    private fun createFakeTopRatedNetworkModel(
        backdropPath: String?,
        posterPath: String?
    ): TopRatedNetworkModel {
        return TopRatedNetworkModel(
            backdropPath,
            "2021-09-03",
            listOf(
                10764
            ),
            130392,
            "The D'Amelio Show",
            listOf("US"),
            "en",
            "The D'Amelio Show",
            "From relative obscurity and a seemingly normal life, to overnight success and thrust into the Hollywood limelight overnight, the D’Amelios are faced with new challenges and opportunities they could not have imagined.",
            15.344,
            posterPath,
            8.9,
            3186
        )
    }
}