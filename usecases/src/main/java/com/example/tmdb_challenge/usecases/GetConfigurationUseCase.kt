package com.example.tmdb_challenge.usecases

import com.example.tmdb_challenge.data.repository.MovieRepository
import javax.inject.Inject

class GetConfigurationUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun invoke() {
        movieRepository.updateLanguages()
        movieRepository.updateUpcomingMovies()
        movieRepository.updateTopRatedMovies()
    }
}