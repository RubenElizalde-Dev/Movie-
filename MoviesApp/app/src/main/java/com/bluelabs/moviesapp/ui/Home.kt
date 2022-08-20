package com.bluelabs.moviesapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluelabs.moviesapp.adapters.NowPlayingMoviesAdapter
import com.bluelabs.moviesapp.adapters.PopularMoviesAdapter
import com.bluelabs.moviesapp.adapters.TopRatedMoviesAdapter
import com.bluelabs.moviesapp.databinding.FragmentHomeBinding
import com.bluelabs.moviesapp.domain.api.ApiInterface
import com.bluelabs.moviesapp.domain.api.ApiUtilities
import com.bluelabs.moviesapp.domain.model.*
import com.bluelabs.moviesapp.utils.InternetUtils
import com.bluelabs.moviesapp.viewmodel.HomeViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch


class Home : Fragment() {

    private val apiInterface: ApiInterface =
        ApiUtilities.getInstace().create(ApiInterface::class.java)
    private val homeViewModel = HomeViewModel(apiInterface)
    private lateinit var binding: FragmentHomeBinding
    private lateinit var topRatedMoviesAdapter: TopRatedMoviesAdapter
    private lateinit var nowPlayingMoviesAdapter: NowPlayingMoviesAdapter
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter
    private val gson = Gson()
    private val internetUtils = InternetUtils()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val sharedPref = this.requireActivity()
            .getSharedPreferences("Data", Context.MODE_PRIVATE)

        viewLifecycleOwner.lifecycleScope.launch {

            val datatopRatedMovies = sharedPref.getString("topRatedMovies", null)
            val datanowPlayingMovies = sharedPref.getString("nowPlayingMovies", null)
            val datapopularMovies = sharedPref.getString("popularMovies", null)

            if (datatopRatedMovies != null) {
                homeViewModel.topRatedMovies.postValue(
                    gson.fromJson(
                        datatopRatedMovies,
                        TopRatedMovies::class.java
                    )
                )
            }
            if (datanowPlayingMovies != null) {
                homeViewModel.nowPlayingMovies.postValue(
                    gson.fromJson(
                        datanowPlayingMovies,
                        NowPlayingMovies::class.java
                    )
                )
            }
            if (datapopularMovies != null) {
                homeViewModel.popularMovies.postValue(
                    gson.fromJson(
                        datapopularMovies,
                        PopularMovies::class.java
                    )
                )
            }

            if (internetUtils.isNetworkAvailable(requireActivity())){
                homeViewModel.getTopRatedMoviesAPI();
                homeViewModel.getNowPlayingMoviesAPI();
                homeViewModel.getPopularMoviesAPI();
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){

        }

        homeViewModel.topRatedMovies.observe(viewLifecycleOwner) {
            with(sharedPref.edit()) {
                putString("topRatedMovies", gson.toJson(it))
                apply()
            }
            initTopRatedMovies(it.results)
        }

        homeViewModel.nowPlayingMovies.observe(viewLifecycleOwner) {
            with(sharedPref.edit()) {
                putString("nowPlayingMovies", gson.toJson(it))
                apply()
            }
            initNowPlayingMovies(it.results)
        }

        homeViewModel.popularMovies.observe(viewLifecycleOwner) {
            with(sharedPref.edit()) {
                putString("popularMovies", gson.toJson(it))
                apply()
            }
            initPopularMovies(it.results)
        }

        binding.txtSearch.doOnTextChanged { text, start, before, count ->
            try {
                if (text!!.isNotEmpty()) {
                    try {
                        val filtertopmovies =
                            homeViewModel.topRatedMovies.value!!.results.filter { name ->
                                name.originalTitle!!.lowercase()
                                    .contains(text.toString().lowercase())
                            }
                        if (filtertopmovies.isNotEmpty()) {
                            initTopRatedMovies(filtertopmovies)
                            binding.topRatedMoviesList.visibility = View.VISIBLE
                            binding.topRatedMoviesListText.visibility = View.VISIBLE
                        } else {
                            initTopRatedMovies(arrayListOf())
                            binding.topRatedMoviesList.visibility = View.INVISIBLE
                            binding.topRatedMoviesListText.visibility = View.INVISIBLE
                        }

                    } catch (e: Exception) {

                    }

                    try {
                        val filternowmovies =
                            homeViewModel.nowPlayingMovies.value!!.results.filter { name ->
                                name.originalTitle!!.lowercase()
                                    .contains(text.toString().lowercase())
                            }
                        if (filternowmovies.isNotEmpty()) {
                            initNowPlayingMovies(filternowmovies)
                            binding.nowPlayingMoviesListText.visibility = View.VISIBLE
                            binding.nowPlayingMoviesList.visibility = View.VISIBLE
                        } else {
                            initNowPlayingMovies(arrayListOf())
                            binding.nowPlayingMoviesList.visibility = View.INVISIBLE
                            binding.nowPlayingMoviesListText.visibility = View.INVISIBLE
                        }
                    } catch (e: Exception) {

                    }

                    try {
                        val filterpopularmovies =
                            homeViewModel.popularMovies.value!!.results.filter { name ->
                                name.originalTitle!!.lowercase()
                                    .contains(text.toString().lowercase())
                            }
                        if (filterpopularmovies.isNotEmpty()) {
                            initPopularMovies(filterpopularmovies)
                            binding.popularMoviesList.visibility = View.VISIBLE
                            binding.popularMoviesListText.visibility = View.VISIBLE
                        } else {
                            initPopularMovies(arrayListOf())
                            binding.popularMoviesList.visibility = View.INVISIBLE
                            binding.popularMoviesListText.visibility = View.INVISIBLE
                        }
                    } catch (e: Exception) {

                    }

                } else {
                    initTopRatedMovies(homeViewModel.topRatedMovies.value!!.results)
                    initNowPlayingMovies(homeViewModel.nowPlayingMovies.value!!.results)
                    initPopularMovies(homeViewModel.popularMovies.value!!.results)
                }

            } catch (e: Exception) {

            }
        }
        return binding.root
    }


    private fun goToMovieDetail(movieID: String) {
        var action = HomeDirections.actionHomeToMovieDetail(movieID + "")
        findNavController().navigate(action)
    }

    private fun initTopRatedMovies(list: List<Results>) {
        topRatedMoviesAdapter = TopRatedMoviesAdapter(list)
        topRatedMoviesAdapter.onItemClick = { movie ->
            goToMovieDetail(movie.id.toString())
        }
        binding.topRatedMoviesList.adapter = topRatedMoviesAdapter
        binding.topRatedMoviesList.set3DItem(true)
        binding.topRatedMoviesList.setAlpha(true)
        binding.topRatedMoviesList.setInfinite(true)
    }

    private fun initNowPlayingMovies(list: List<Resultss>) {
        binding.nowPlayingMoviesList.setHasFixedSize(true)
        binding.nowPlayingMoviesList.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        nowPlayingMoviesAdapter = NowPlayingMoviesAdapter(list)
        nowPlayingMoviesAdapter.onItemClick = { movie ->
            goToMovieDetail(movie.id.toString())
        }
        binding.nowPlayingMoviesList.adapter = nowPlayingMoviesAdapter
    }

    private fun initPopularMovies(list: List<Resultsss>) {
        binding.popularMoviesList.setHasFixedSize(true)
        binding.popularMoviesList.layoutManager = GridLayoutManager(context, 2)
        popularMoviesAdapter = PopularMoviesAdapter(list)
        popularMoviesAdapter.onItemClick = { movie ->
            goToMovieDetail(movie.id.toString())
        }
        binding.popularMoviesList.adapter = popularMoviesAdapter
    }

}