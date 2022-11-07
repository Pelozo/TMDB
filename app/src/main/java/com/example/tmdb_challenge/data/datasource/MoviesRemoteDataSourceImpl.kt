package com.example.tmdb_challenge.data.datasource

import com.example.tmdb_challenge.api.MoviesService
import com.example.tmdb_challenge.data.mappers.mapToModel
import com.example.tmdb_challenge.data.util.safeApiCall
import javax.inject.Inject
import com.example.tmdb_challenge.data.util.Result
import com.example.tmdb_challenge.domain.models.Language
import com.example.tmdb_challenge.domain.models.Movie

class MoviesRemoteDataSourceImpl @Inject constructor(
    private val moviesService: MoviesService
) : MoviesRemoteDataSource {
    override suspend fun getUpcomingMovies(): Result<List<Movie>> {
        return safeApiCall {
            moviesService.getUpcomingMovies()
                .results
                .map {
                    it.mapToModel()
                }
        }
    }

    override suspend fun getTopRatedMovies(): Result<List<Movie>> {
        return safeApiCall {
            moviesService.getTopRated()
                .results
                .map {
                    it.mapToModel()
                }
        }
    }

    override suspend fun getLanguages(): Result<List<Language>> {
        return safeApiCall {
            moviesService.getLanguages()
                .map {
                    it.mapToModel()
                }
        }
    }

    override suspend fun getMovieTrailer(movieId: String): Result<List<String>> {
        return safeApiCall {
            moviesService.getVideoByMovie(movieId).results.map {
                it.key
            }
        }
    }
}
