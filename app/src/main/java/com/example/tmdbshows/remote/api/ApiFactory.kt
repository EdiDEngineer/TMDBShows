package com.example.tmdbshows.remote.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {

    private const val API_KEY = "25a8f80ba018b52efb64f05140f6b43c"

    fun createTMDBApi(retrofit: Retrofit): TMDBApi {
        return retrofit
            .create(TMDBApi::class.java)
    }

    fun createRetrofit(
        okHttpClient: OkHttpClient,
        baseUrl: HttpUrl
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    fun createBaseUrl(): HttpUrl {
        return HttpUrl.Builder()
            .scheme("https")
            .host("api.themoviedb.org")
            .build()
    }

    fun createOkHttpClient(
        loggingInterceptor: Interceptor,
        requestInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(requestInterceptor)
            .build()
    }

    fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    fun createRequestInterceptor(): Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val url = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val requestBuilder = originalRequest.newBuilder().url(url)

        chain.proceed(requestBuilder.build())
    }
}