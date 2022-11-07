package com.example.tmdb_challenge.di

import com.example.tmdb_challenge.data.datasource.MoviesLocalDataSource
import com.example.tmdb_challenge.data.datasource.MoviesLocalDataSourceImpl
import com.example.tmdb_challenge.data.datasource.MoviesRemoteDataSource
import com.example.tmdb_challenge.data.datasource.MoviesRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {

    @Provides
    fun providesMoviesRemoteDataSource(moviesRemoteDataSourceImpl: MoviesRemoteDataSourceImpl): MoviesRemoteDataSource = moviesRemoteDataSourceImpl

    @Provides
    fun providesMoviesLocalDataSource(moviesLocalDataSourceImpl: MoviesLocalDataSourceImpl): MoviesLocalDataSource = moviesLocalDataSourceImpl
}