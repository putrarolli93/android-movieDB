package com.example.testapp.repository

import com.example.testapp.network.ApiService

class MainRepository(private val service: ApiService) {

    suspend fun getPopularMovies() = service.getPopularMovies()

}