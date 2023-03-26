package com.example.tmdbshows.di

import com.example.tmdbshows.remote.TMDBApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class InterceptorOkHttpClient

    @Singleton
    @Provides
    fun provideTMDBApi(
        @InterceptorOkHttpClient okHttpClient: OkHttpClient
    ): TMDBApi {
        return TMDBApi.create(okHttpClient)
    }

    @InterceptorOkHttpClient
    @Provides
    fun provideInterceptorOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().retryOnConnectionFailure(true)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

}