package com.bluelabs.moviesapp.domain.api

import com.bluelabs.moviesapp.domain.model.MovieDetail
import com.bluelabs.moviesapp.domain.model.NowPlayingMovies
import com.bluelabs.moviesapp.domain.model.PopularMovies
import com.bluelabs.moviesapp.domain.model.TopRatedMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("movie/top_rated?")
    suspend fun getTopRatedMovies(@Query("api_key") api_key: String?): Response<TopRatedMovies>

    @GET("movie/now_playing?")
    suspend fun getNowPlayingMovies(@Query("api_key") api_key: String?): Response<NowPlayingMovies>

    @GET("movie/popular?")
    suspend fun getPopularMovies(@Query("api_key") api_key: String?): Response<PopularMovies>

    @GET("movie/{movieID}?")
    suspend fun getMovieDetails(
        @Path("movieID") movieID: String,
        @Query("api_key") api_key: String?
    ): Response<MovieDetail>
}