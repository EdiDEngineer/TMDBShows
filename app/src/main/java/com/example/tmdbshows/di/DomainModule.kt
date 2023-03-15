package com.example.tmdbshows.di


import com.example.tmdbshows.domain.contract.TMDBRepo
import com.example.tmdbshows.domain.impl.TMDBRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindTMDBRepository(repository: TMDBRepoImpl): TMDBRepo

}