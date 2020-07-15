package com.example.testapp.network

import com.example.testapp.model.MovieListModel
import com.example.testapp.model.MovieReviewModel
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val service: ApiService) {

    fun getTopMovie(onResult: (isSuccess: Boolean, response: MovieListModel?) -> Unit) {
        service.getTopMovies().enqueue(object : Callback<MovieListModel> {
            override fun onResponse(call: Call<MovieListModel>?, response: Response<MovieListModel>?) {
                if (response != null && response.isSuccessful)
                    onResult(true, response.body())
                else
                    onResult(false, null)
            }

            override fun onFailure(call: Call<MovieListModel>?, t: Throwable?) {
                onResult(false, null)
            }
        })
    }

    fun getNowPlayingMovie(onResult: (isSuccess: Boolean, response: MovieListModel?) -> Unit) {
        service.getNowPlayingMovies().enqueue(object : Callback<MovieListModel> {
            override fun onResponse(call: Call<MovieListModel>?, response: Response<MovieListModel>?) {
                if (response != null && response.isSuccessful)
                    onResult(true, response.body())
                else
                    onResult(false, null)
            }

            override fun onFailure(call: Call<MovieListModel>?, t: Throwable?) {
                onResult(false, null)
            }
        })
    }

    fun getReviews(id: Int, onResult: (isSuccess: Boolean, response: MovieReviewModel?) -> Unit) {
        service.getReviews(id).enqueue(object : Callback<MovieReviewModel> {
            override fun onResponse(call: Call<MovieReviewModel>?, response: Response<MovieReviewModel>?) {
                if (response != null && response.isSuccessful)
                    onResult(true, response.body())
                else
                    onResult(false, null)
            }

            override fun onFailure(call: Call<MovieReviewModel>?, t: Throwable?) {
                onResult(false, null)
            }
        })
    }
}