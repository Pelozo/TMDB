package com.example.tmdb_challenge.domain.models.responses

import com.google.gson.annotations.SerializedName

data class LanguageResponse(
    @SerializedName("iso_639_1")
    val iso: String,

    @SerializedName("english_name")
    val englishName: String,

    @SerializedName("name")
    val name: String,
)