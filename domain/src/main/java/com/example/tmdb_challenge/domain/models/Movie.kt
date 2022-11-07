package com.example.tmdb_challenge.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
    val movieId: Long,
    val backdropPath: String,
    val originalLanguage: String,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: String,
    var list: String? = null
): Parcelable
