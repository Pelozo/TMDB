package com.example.tmdb_challenge.api

import com.example.tmdb_challenge.domain.models.responses.LanguageResponse
import com.example.tmdb_challenge.domain.models.responses.MovieTrailersResponse
import com.example.tmdb_challenge.domain.models.responses.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesService {
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
    ): MoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRated(
    ): MoviesResponse

    @GET("movie/{movieId}/videos")
    suspend fun getVideoByMovie(
        @Path("movieId") movieId: String
    ): MovieTrailersResponse

    // TODO move this
    @GET("configuration/languages")
    suspend fun getLanguages(
    ): List<LanguageResponse>
}