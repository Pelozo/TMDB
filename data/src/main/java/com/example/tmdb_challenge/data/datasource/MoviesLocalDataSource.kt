package com.example.tmdb_challenge.data.datasource

import com.example.tmdb_challenge.domain.models.Language
import com.example.tmdb_challenge.domain.models.Movie
import com.example.tmdb_challenge.domain.models.entitities.MovieListWithMoviesCrossRef
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {
    fun getMovies(): Flow<List<Movie>>
    suspend fun saveMovies(movies: List<Movie>): List<Long>
    suspend fun saveMovieToList(movie: MovieListWithMoviesCrossRef)
    suspend fun saveLanguages(langauges: List<Language>)
    fun getLanguages(): Flow<List<Language>>
}