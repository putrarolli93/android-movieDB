package com.example.testapp.network.repository

import com.example.testapp.network.model.MovieListModel
import com.example.testapp.network.ApiService
import io.reactivex.Single

class MainRepository(private val service: ApiService) {

    suspend fun getPopularMovies() = service.getPopularMovies()

    fun getTopMovie(): Single<MovieListModel> {
        return service.getTopMovies()
    }

}