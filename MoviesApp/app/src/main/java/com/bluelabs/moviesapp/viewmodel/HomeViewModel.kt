package com.bluelabs.moviesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.bluelabs.moviesapp.BuildConfig
import com.bluelabs.moviesapp.domain.api.ApiInterface
import com.bluelabs.moviesapp.domain.model.*

class HomeViewModel(private val apiInterface: ApiInterface) {

    val topRatedMovies = MutableLiveData<TopRatedMovies>()
    val nowPlayingMovies = MutableLiveData<NowPlayingMovies>()
    val popularMovies = MutableLiveData<PopularMovies>()

    suspend fun getTopRatedMoviesAPI() {
        val result = apiInterface.getTopRatedMovies(BuildConfig.MOVIEDB_API_KEY);
        if (result.body() != null) {
            topRatedMovies.postValue(result.body())
        }
    }

    suspend fun getNowPlayingMoviesAPI() {
        val result = apiInterface.getNowPlayingMovies(BuildConfig.MOVIEDB_API_KEY);
        if (result.body() != null) {
            nowPlayingMovies.postValue(result.body())
        }
    }

    suspend fun getPopularMoviesAPI() {
        val result = apiInterface.getPopularMovies(BuildConfig.MOVIEDB_API_KEY);
        if (result.body() != null) {
            popularMovies.postValue(result.body())
        }
    }
}