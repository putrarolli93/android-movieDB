package com.example.testapp.network

import com.example.testapp.network.model.MovieListModel
import com.example.testapp.network.model.MovieReviewModel
import com.example.testapp.utils.Constants
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): MovieListModel

    @GET("top_rated")
    fun getTopMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Single<MovieListModel>

    @GET("now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<MovieListModel>

    @GET("{movie_id}/reviews")
    fun getReviews(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<MovieReviewModel>

}