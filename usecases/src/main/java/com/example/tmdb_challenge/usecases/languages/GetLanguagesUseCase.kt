package com.example.tmdb_challenge.usecases.languages

import com.example.tmdb_challenge.data.repository.MovieRepository
import com.example.tmdb_challenge.domain.models.Language
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLanguagesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun invoke(): Flow<List<Language>> =
        movieRepository.getLanguages()
}