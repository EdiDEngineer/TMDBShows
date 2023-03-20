package com.example.tmdbshows.di

import com.example.tmdbshows.remote.TMDBApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    private const val API_KEY = "25a8f80ba018b52efb64f05140f6b43c"

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class InterceptorOkHttpClient
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class LoggingInterceptor
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RequestInterceptor
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseTMDBUrl

    @Singleton
    @Provides
    fun provideTMDBApi(
        @InterceptorOkHttpClient okHttpClient: OkHttpClient,
        @BaseTMDBUrl baseUrl: HttpUrl
    ): TMDBApi {
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
            .create(TMDBApi::class.java)
    }

    @BaseTMDBUrl
    @Provides
    fun provideBaseUrl(): HttpUrl {
        return HttpUrl.Builder()
            .scheme("https")
            .host("api.themoviedb.org")
            .build()
    }

    @InterceptorOkHttpClient
    @Provides
    fun provideOkHttpClient(
        @LoggingInterceptor loggingInterceptor: Interceptor,
        @RequestInterceptor requestInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(requestInterceptor)
            .build()
    }

    @LoggingInterceptor
    @Provides
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @RequestInterceptor
    @Provides
    fun provideRequestInterceptor(): Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val url = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val requestBuilder = originalRequest.newBuilder().url(url)

        chain.proceed(requestBuilder.build())
    }
}