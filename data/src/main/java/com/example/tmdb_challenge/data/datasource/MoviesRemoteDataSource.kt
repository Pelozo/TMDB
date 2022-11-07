package com.example.tmdb_challenge.data.datasource

import com.example.tmdb_challenge.data.util.Result
import com.example.tmdb_challenge.domain.models.Language
import com.example.tmdb_challenge.domain.models.Movie

interface MoviesRemoteDataSource {
    suspend fun getUpcomingMovies(): Result<List<Movie>>
    suspend fun getTopRatedMovies(): Result<List<Movie>>
    suspend fun getLanguages(): Result<List<Language>>
    suspend fun getMovieTrailer(movieId: String): Result<List<String>>
}