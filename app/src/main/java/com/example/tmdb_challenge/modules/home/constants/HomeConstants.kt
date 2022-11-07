package com.example.tmdb_challenge.modules.home.constants

class HomeConstants {
    companion object {
        const val NO_LANGUAGE_ISO = "xx"
        const val NO_YEAR = "xx"
        const val DATE_DELIMITER = "-"
        const val RECOMMENDED_LIMIT = 6

        enum class MovieListType(val type: String) {
            TOP_RATED("TOP_RATED"),
            UPCOMING("UPCOMING"),

        }
    }
}