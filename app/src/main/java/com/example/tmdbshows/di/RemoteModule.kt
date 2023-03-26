package com.example.tmdbshows.di

import com.example.tmdbshows.remote.api.ApiFactory
import com.example.tmdbshows.remote.api.TMDBApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class LoggingInterceptor

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RequestInterceptor

    @Singleton
    @Provides
    fun provideTMDBApi(retrofit: Retrofit): TMDBApi = ApiFactory.createTMDBApi(retrofit)

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: HttpUrl): Retrofit =
        ApiFactory.createRetrofit(okHttpClient, baseUrl)

    @Provides
    fun provideBaseUrl(): HttpUrl = ApiFactory.createBaseUrl()

    @Provides
    fun provideOkHttpClient(
        @LoggingInterceptor loggingInterceptor: Interceptor,
        @RequestInterceptor requestInterceptor: Interceptor
    ): OkHttpClient = ApiFactory.createOkHttpClient(loggingInterceptor, requestInterceptor)

    @LoggingInterceptor
    @Provides
    fun provideLoggingInterceptor(): Interceptor = ApiFactory.createLoggingInterceptor()

    @RequestInterceptor
    @Provides
    fun provideRequestInterceptor(): Interceptor = ApiFactory.createRequestInterceptor()
}