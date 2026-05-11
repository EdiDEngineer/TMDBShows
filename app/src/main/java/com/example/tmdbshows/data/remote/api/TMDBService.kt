package com.example.tmdbshows.data.remote.api

import com.example.tmdbshows.data.remote.model.networkresponse.TopRatedNetworkResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {

    @GET("/3/tv/top_rated")
    suspend fun getTopRated(
        @Query("page") page: Int,
        @Query("language") language: String
    ): Response<TopRatedNetworkResponse>
}