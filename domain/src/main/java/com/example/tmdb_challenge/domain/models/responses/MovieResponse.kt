package com.example.tmdb_challenge.domain.models.responses

import com.google.gson.annotations.SerializedName

data class MovieResponse(

    @SerializedName("id")
    val id: Long,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val title: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("vote_average")
    val voteAverage: String,
)
