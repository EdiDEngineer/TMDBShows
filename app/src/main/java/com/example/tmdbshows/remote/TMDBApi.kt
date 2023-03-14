package com.example.tmdbshows.remote

import com.example.tmdbshows.remote.model.networkresponse.TopRatedNetworkResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {

    @GET("/3/tv/top_rated")
    suspend fun getTopRated(
        @Query("page") page: Int,
        @Query("language") language: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<TopRatedNetworkResponse>

    companion object {

        private const val API_KEY = "25a8f80ba018b52efb64f05140f6b43c"

        private val baseUrl = HttpUrl.Builder()
            .scheme("https")
            .host("api.themoviedb.org")
            .build()

        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        private val okHttpClient = OkHttpClient.Builder().retryOnConnectionFailure(true)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        fun create(client: OkHttpClient = okHttpClient): TMDBApi = create(baseUrl, client)

        private fun create(httpUrl: HttpUrl, client: OkHttpClient): TMDBApi {
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(TMDBApi::class.java)
        }
    }
}