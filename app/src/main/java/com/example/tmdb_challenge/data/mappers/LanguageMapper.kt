package com.example.tmdb_challenge.data.mappers

import com.example.tmdb_challenge.domain.models.Language
import com.example.tmdb_challenge.domain.models.entitities.LanguageEntity
import com.example.tmdb_challenge.domain.models.responses.LanguageResponse

fun LanguageResponse.mapToModel() =
    Language(
        iso,
        englishName,
        name
    )

fun Language.mapToEntity() =
    LanguageEntity(
        iso,
        englishName,
        name
    )

fun LanguageEntity.mapToModel() =
    Language(
        iso,
        englishName,
        name
    )