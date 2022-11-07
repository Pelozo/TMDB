package com.example.tmdb_challenge.modules.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tmdb_challenge.base.BaseViewModel
import com.example.tmdb_challenge.domain.models.Language
import com.example.tmdb_challenge.domain.models.Movie
import com.example.tmdb_challenge.modules.home.constants.HomeConstants
import com.example.tmdb_challenge.modules.home.constants.HomeConstants.Companion.DATE_DELIMITER
import com.example.tmdb_challenge.modules.home.constants.HomeConstants.Companion.NO_LANGUAGE_ISO
import com.example.tmdb_challenge.modules.home.constants.HomeConstants.Companion.NO_YEAR
import com.example.tmdb_challenge.modules.home.constants.HomeConstants.Companion.RECOMMENDED_LIMIT
import com.example.tmdb_challenge.usecases.languages.GetLanguagesUseCase
import com.example.tmdb_challenge.usecases.movies.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getLanguagesUseCase: GetLanguagesUseCase
) : BaseViewModel() {
    private val moviesUpcomingMLD = MutableLiveData<List<Movie>>()
    private val moviesTopRatedMLD = MutableLiveData<List<Movie>>()
    private val moviesRecommendedMLD = MutableLiveData<List<Movie>>()
    private val moviesLanguagesMLD = MutableLiveData<List<Language>>()
    private val selectedLanguageMLD = MutableLiveData<Language>()
    private val moviesYearsMLD = MutableLiveData<List<String>>()
    private val selectedYearMLD = MutableLiveData<String>()

    fun setUp(){
        getMovies()
        getLanguages()
    }

    fun setRecommendedLanguage(lang: Language) {
        selectedLanguageMLD.value = lang
        filterRecommended()
    }

    fun setRecommendedYear(year: String) {
        selectedYearMLD.value = year
        filterRecommended()
    }

    private fun filterRecommended() {
        println("filtering")
        val topRated = moviesTopRatedMLD.value
        // By language
        var recommended = if (selectedLanguageMLD.value?.iso == NO_LANGUAGE_ISO)
            topRated
        else topRated?.filter { movie ->
            movie.originalLanguage == selectedLanguageMLD.value?.iso
        }
        // By year
        recommended = if (selectedYearMLD.value == NO_YEAR)
            recommended
        else recommended?.filter { movie ->
            movie.releaseDate.substringBefore(DATE_DELIMITER) == selectedYearMLD.value
        }
        moviesRecommendedMLD.postValue(recommended?.take(RECOMMENDED_LIMIT) ?: emptyList())
    }

    private fun getMovies() {
        viewModelScope.launch {
            getMoviesUseCase.invoke()
                .collect { movies ->
                    val upcomingMovies = movies.filter { it.list == HomeConstants.Companion.MovieListType.UPCOMING.toString() }
                    moviesUpcomingMLD.postValue(upcomingMovies)

                    val topRatedMovies = movies.filter { it.list == HomeConstants.Companion.MovieListType.TOP_RATED.toString() }
                    moviesTopRatedMLD.postValue(topRatedMovies)

                    val recommendedMovies = topRatedMovies.take(6)

                    moviesRecommendedMLD.postValue(recommendedMovies)

                    val movieYears =
                        topRatedMovies.map { it.releaseDate.substringBefore(DATE_DELIMITER)}
                            .sorted()
                            .distinct()

                    val result = movieYears.toMutableList()
                    result.add(0, NO_YEAR)
                    moviesYearsMLD.value = result
                    filterRecommended()
                }
        }
    }

    private fun getLanguages() {
        viewModelScope.launch {
            getLanguagesUseCase.invoke().collect {
                val languageList = it.toMutableList()
                val noLanguage = it.first{ lang -> lang.iso == NO_LANGUAGE_ISO}
                languageList.remove(noLanguage)
                languageList.sortedBy { lang -> lang.englishName }
                languageList.add(0, noLanguage)
                moviesLanguagesMLD.postValue(languageList)
            }
        }
    }


    fun moviesUpcoming(): LiveData<List<Movie>> = moviesUpcomingMLD
    fun moviesTopRated(): LiveData<List<Movie>> = moviesTopRatedMLD
    fun moviesRecommended(): LiveData<List<Movie>> = moviesRecommendedMLD
    fun moviesLanguages(): LiveData<List<Language>> = moviesLanguagesMLD
    fun moviesYears(): LiveData<List<String>> = moviesYearsMLD

}