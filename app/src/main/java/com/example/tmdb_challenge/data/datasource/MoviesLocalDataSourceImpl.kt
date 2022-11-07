package com.example.tmdb_challenge.data.datasource

import com.example.tmdb_challenge.data.db.LanguagesDao
import com.example.tmdb_challenge.data.db.MoviesDao
import com.example.tmdb_challenge.data.mappers.mapToEntity
import com.example.tmdb_challenge.data.mappers.mapToModel
import com.example.tmdb_challenge.domain.models.Language
import com.example.tmdb_challenge.domain.models.Movie
import com.example.tmdb_challenge.domain.models.entitities.MovieListWithMoviesCrossRef
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MoviesLocalDataSourceImpl @Inject constructor(
    private val moviesDao: MoviesDao,
    private val languagesDao: LanguagesDao
) : MoviesLocalDataSource {
    override fun getMovies(): Flow<List<Movie>> {
        return moviesDao.getAll().map {
            it.flatMap { list ->
                list.movies.map { movie ->
                    val movieModel = movie.mapToModel()
                    movieModel.list = list.movieList.listName
                    movieModel
                }
            }
        }
    }


    override suspend fun saveMovies(movies: List<Movie>) =
        moviesDao.insertMovies(movies.map { it.mapToEntity() })

    override suspend fun saveMovieToList(movie: MovieListWithMoviesCrossRef) {
        moviesDao.insertMovieIntoList(movie)
    }

    //TOOD move languages stuff
    override suspend fun saveLanguages(langauges: List<Language>) {
        languagesDao.insertAll(langauges.map { it.mapToEntity() })
    }

    override fun getLanguages(): Flow<List<Language>> =
        languagesDao.getAll().map { it.map { languageEntity -> languageEntity.mapToModel() } }
}