package com.example.modules.home.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tmdb_challenge.domain.models.Language
import com.example.tmdb_challenge.domain.models.Movie
import com.example.tmdb_challenge.modules.home.constants.HomeConstants.Companion.NO_YEAR
import com.example.tmdb_challenge.modules.home.viewModel.HomeViewModel
import com.example.tmdb_challenge.usecases.languages.GetLanguagesUseCase
import com.example.tmdb_challenge.usecases.movies.GetMoviesUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class HomeVIewModelTest{


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getMoviesUseCase: GetMoviesUseCase
    @Mock
    lateinit var getLanguagesUseCase: GetLanguagesUseCase

    lateinit var viewmodel: HomeViewModel


    // Shouldn't be here
    val movieList = listOf(
        Movie (
            movieId = 10L,
            backdropPath = "path",
            originalLanguage = "en",
            title = "movieTitle",
            overview = "It's a movie",
            posterPath = "poster/path",
            releaseDate = "2019-05-09",
            voteAverage = "1.5",
            list = "TOP_RATED"
        ),
        Movie (
            movieId = 20L,
            backdropPath = "path",
            originalLanguage = "es",
            title = "movieTitle",
            overview = "It's a movie",
            posterPath = "poster/path",
            releaseDate = "2019-05-09",
            voteAverage = "1.5",
            list = "TOP_RATED"
        ),
        Movie (
            movieId = 30L,
            backdropPath = "path",
            originalLanguage = "en",
            title = "movieTitle",
            overview = "It's a movie",
            posterPath = "poster/path",
            releaseDate = "2019-05-09",
            voteAverage = "1.5",
            list = "TOP_RATED"
        ),
        Movie (
            movieId = 40L,
            backdropPath = "path",
            originalLanguage = "ja",
            title = "movieTitle",
            overview = "It's a movie",
            posterPath = "poster/path",
            releaseDate = "2019-05-09",
            voteAverage = "1.5",
            list = "TOP_RATED"
        )
    )

    val languageEn = Language(
        iso = "en",
        englishName = "English",
        name = "English"
    )

    val languageEs = Language(
        iso = "es",
        englishName = "Spanish",
        name = "Español"
    )

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        viewmodel = HomeViewModel(getMoviesUseCase, getLanguagesUseCase)
    }

    @After
    fun after(){
        Mockito.validateMockitoUsage();
    }

    @Test
    fun `moviesRecommended returns empty list successfully`() = runBlocking {
        //given
        Mockito.`when`(getMoviesUseCase.invoke()).thenReturn(flow {emit(movieList)})
        Mockito.`when`(getLanguagesUseCase.invoke()).thenReturn(flow {emit(emptyList())})

        //when
        viewmodel.setUp()
        viewmodel.setRecommendedLanguage(languageEn)
        viewmodel.setRecommendedYear("12098465120")
        //then
        assertEquals(
            emptyList<Movie>(),
            viewmodel.moviesRecommended().value
        )
    }


    @Test
    fun `moviesRecommended returns english movie list successfully`() = runBlocking {
        //given
        Mockito.`when`(getMoviesUseCase.invoke()).thenReturn(flow {emit(movieList)})
        Mockito.`when`(getLanguagesUseCase.invoke()).thenReturn(flow {emit(emptyList())})

        //when
        viewmodel.setUp()
        viewmodel.setRecommendedLanguage(languageEn)
        viewmodel.setRecommendedYear(NO_YEAR)
        //then
        assertEquals(
            movieList.filter { it.originalLanguage == "en" },
            viewmodel.moviesRecommended().value
        )
    }


}
