package com.example.tmdbshows.data.remote.model.networkresponse

import com.example.tmdbshows.data.remote.model.networkmodel.TopRatedNetworkModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopRatedNetworkResponse(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val topRatedNetworkModel: List<TopRatedNetworkModel>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)