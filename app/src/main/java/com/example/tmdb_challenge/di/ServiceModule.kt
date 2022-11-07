package com.example.tmdb_challenge.di

import com.example.tmdb_challenge.api.MoviesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {

    @Provides
    fun provideMoviesService(retrofit: Retrofit): MoviesService =
        retrofit.create(MoviesService::class.java)
}