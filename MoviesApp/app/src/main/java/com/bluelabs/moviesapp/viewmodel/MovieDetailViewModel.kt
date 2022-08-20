package com.bluelabs.moviesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.bluelabs.moviesapp.BuildConfig
import com.bluelabs.moviesapp.domain.api.ApiInterface
import com.bluelabs.moviesapp.domain.model.MovieDetail

class MovieDetailViewModel(private val apiInterface: ApiInterface) {
    val movieDetail = MutableLiveData<MovieDetail>()

    suspend fun getMovieDetailAPI(movieID: String) {
        val result = apiInterface.getMovieDetails(movieID, BuildConfig.MOVIEDB_API_KEY);
        if (result.body() != null) {
            movieDetail.postValue(result.body())
        }
    }

}