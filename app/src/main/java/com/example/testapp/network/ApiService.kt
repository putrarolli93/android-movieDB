package com.example.testapp.network

import com.example.testapp.model.MovieListModel
import com.example.testapp.model.ProvinsiModel
import com.example.testapp.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<MovieListModel>

    @GET("top_rated")
    fun getTopMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<MovieListModel>

    @GET("now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<MovieListModel>
}