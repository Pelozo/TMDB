package com.example.tmdb_challenge.domain.models.entitities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity (
    @PrimaryKey
    val movieId: Long,
    val backdropPath: String,
    val originalLanguage: String,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: String
)