package com.example.tmdb_challenge.di

import com.example.tmdb_challenge.BuildConfig
import com.example.tmdb_challenge.data.interceptors.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
    ): OkHttpClient = OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()

    @Provides
    fun provideAuthInterceptor() = AuthInterceptor()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.TMDB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
}