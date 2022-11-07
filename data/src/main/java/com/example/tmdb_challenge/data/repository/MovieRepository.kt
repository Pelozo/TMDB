package com.example.tmdb_challenge.data.repository

import com.example.tmdb_challenge.domain.models.Language
import com.example.tmdb_challenge.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun updateUpcomingMovies(): Boolean
    suspend fun updateTopRatedMovies(): Boolean
    suspend fun updateLanguages(): Boolean
    suspend fun getMovieTrailer(movieId: String): String?
    fun getLanguages(): Flow<List<Language>>
    fun getMovies(): Flow<List<Movie>>
}