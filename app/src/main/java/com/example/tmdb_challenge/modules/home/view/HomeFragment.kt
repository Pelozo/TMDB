package com.example.tmdb_challenge.modules.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb_challenge.R
import com.example.tmdb_challenge.base.BaseFragment
import com.example.tmdb_challenge.databinding.FragmentHomeBinding
import com.example.tmdb_challenge.domain.models.Language
import com.example.tmdb_challenge.domain.models.Movie
import com.example.tmdb_challenge.modules.home.constants.HomeConstants.Companion.NO_LANGUAGE_ISO
import com.example.tmdb_challenge.modules.home.constants.HomeConstants.Companion.NO_YEAR
import com.example.tmdb_challenge.modules.home.view.adapters.MovieAdapter
import com.example.tmdb_challenge.modules.home.viewModel.HomeViewModel
import com.example.tmdb_challenge.util.observe
import com.example.tmdb_challenge.util.withViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val upcomingAdapter = MovieAdapter { movie ->
        navigateToDetails(movie)
    }

    private val topRatedAdapter = MovieAdapter { movie ->
        navigateToDetails(movie)
    }

    private val recommendedAdapter = MovieAdapter { movie ->
        navigateToDetails(movie)
    }

    private var languages = mutableListOf<Language>()
    private lateinit var languageAdapter: ArrayAdapter<String>

    private var years = mutableListOf<String>()
    private lateinit var yearsAdapter: ArrayAdapter<String>

    override fun setUp(arguments: Bundle?) {
        super.setUp(arguments)
        getViewModel().setUp()
    }

    override fun initView() {
        super.initView()
        with(binding) {
            rvUpcoming.apply {
                adapter = upcomingAdapter
                layoutManager =
                    GridLayoutManager(requireContext(), 1, RecyclerView.HORIZONTAL, false)
            }

            rvTopRated.apply {
                adapter = topRatedAdapter
                layoutManager =
                    GridLayoutManager(requireContext(), 1, RecyclerView.HORIZONTAL, false)
            }

            rvRecommended.apply {
                adapter = recommendedAdapter
                layoutManager =
                    GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            }

            // I hate spinners.
            languageAdapter = ArrayAdapter(
                requireContext(),
                R.layout.item_spinner,
                languages.map { it.englishName })
            spnLanguage.adapter = languageAdapter
            spnLanguage.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    getViewModel().setRecommendedLanguage(languages.first { it.englishName == languages[position].englishName })
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

            yearsAdapter =
                ArrayAdapter(requireContext(),
                    R.layout.item_spinner,
                    years)
            spnYear.adapter = yearsAdapter
            spnYear.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    getViewModel().setRecommendedYear(
                        if (position == 0)
                            NO_YEAR
                        else
                            years.first { it == years[position] }
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    override fun getBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getViewModel(): HomeViewModel = withViewModel {
        observe(moviesUpcoming()) { onMoviesUpcoming(it) }
        observe(moviesTopRated()) { onMoviesTopRated(it) }
        observe(moviesRecommended()) { onMoviesRecommended(it) }
        observe(moviesLanguages()) { onMoviesLanguage(it) }
        observe(moviesYears()) { onMovieYears(it) }
    }

    private fun onMovieYears(yearList: List<String>) {
        years = yearList.map { if (it == NO_YEAR) getString(R.string.year) else it }.toMutableList()
        yearsAdapter.clear()
        yearsAdapter.addAll(years)
        yearsAdapter.notifyDataSetChanged()
    }

    private fun onMoviesUpcoming(movies: List<Movie>) {
        upcomingAdapter.submitList(movies)
    }

    private fun onMoviesTopRated(movies: List<Movie>) {
        topRatedAdapter.submitList(movies)
    }

    private fun onMoviesRecommended(movies: List<Movie>) {
        binding.tvNoResults.isVisible = movies.isEmpty()
        recommendedAdapter.submitList(movies)
    }

    private fun onMoviesLanguage(langs: List<Language>) {
        languages = langs.toMutableList()
        languages.first { it.iso == NO_LANGUAGE_ISO }.englishName = getString(R.string.language)
        languageAdapter.clear()
        languageAdapter.addAll(langs.map { it.englishName })
        binding.spnLanguage.setSelection(languageAdapter.getPosition(languages.first { it.iso == NO_LANGUAGE_ISO }.englishName))
        languageAdapter.notifyDataSetChanged()
    }

    private fun navigateToDetails(movie: Movie) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(
                movie
            )
        )
    }
}