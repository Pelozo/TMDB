package com.example.tmdb_challenge.data.repository

import com.example.tmdb_challenge.data.datasource.MoviesRemoteDataSource
import com.example.tmdb_challenge.data.datasource.MoviesLocalDataSource
import com.example.tmdb_challenge.data.util.Result
import com.example.tmdb_challenge.domain.models.Language
import com.example.tmdb_challenge.domain.models.Movie
import com.example.tmdb_challenge.domain.models.entitities.MovieListWithMoviesCrossRef
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesLocalDataSource: MoviesLocalDataSource
) : MovieRepository {
    override suspend fun updateUpcomingMovies(): Boolean {
        return when (val remoteMovies = withTimeout(5000) {
            moviesRemoteDataSource.getUpcomingMovies()
        }) {
            is Result.Success -> {
                println("saving movies: $remoteMovies")
                moviesLocalDataSource.saveMovies(remoteMovies.data)
                    .onEach { movieId ->
                        moviesLocalDataSource.saveMovieToList(
                            MovieListWithMoviesCrossRef(movieId = movieId, listId = 1)
                        )
                    }
                true
            }
            is Result.Error -> false
        }
    }

    override suspend fun updateTopRatedMovies(): Boolean {
        return when (val remoteMovies = withTimeout(5000) {
            moviesRemoteDataSource.getTopRatedMovies()
        }) {
            is Result.Success -> {
                moviesLocalDataSource.saveMovies(remoteMovies.data)
                    .onEach { movieId ->
                        moviesLocalDataSource.saveMovieToList(
                            MovieListWithMoviesCrossRef(movieId = movieId, listId = 2)
                        )
                    }
                true
            }
            is Result.Error -> false
        }
    }

    override fun getMovies(): Flow<List<Movie>> {
        return moviesLocalDataSource.getMovies()
    }

    //TODO move to different repo
    override suspend fun updateLanguages(): Boolean {
        return when (val remoteLanguages = withTimeout(5000) {
            moviesRemoteDataSource.getLanguages()
        }) {
            is Result.Success -> {
                moviesLocalDataSource.saveLanguages(remoteLanguages.data)
                true
            }
            is Result.Error -> false
        }
    }

    override fun getLanguages(): Flow<List<Language>> {
        return moviesLocalDataSource.getLanguages()
    }

    override suspend fun getMovieTrailer(movieId: String): String? {
        return when (val trailers = withTimeout(5000) {
            moviesRemoteDataSource.getMovieTrailer(movieId)
        }) {
            is Result.Success -> {
                if (trailers.data.isEmpty()) {
                    null
                } else {
                    trailers.data[0]
                }
            }
            is Result.Error -> null
        }
    }

}