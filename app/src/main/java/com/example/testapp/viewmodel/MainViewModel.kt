package com.example.testapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.testapp.model.Movie
import com.example.testapp.model.Reviews
import com.example.testapp.network.Repository
import com.example.testapp.viewmodel.base.BaseViewModel

class MainViewModel(private val repository: Repository) : BaseViewModel() {
    val listPopular = MutableLiveData<MutableList<Movie>>()
    val listTop = MutableLiveData<MutableList<Movie>>()
    val listNowPlaying = MutableLiveData<MutableList<Movie>>()
    val moviewReview = MutableLiveData<MutableList<Reviews>>()

    fun getPopularMovie() {
        dataLoading.value = true
        repository.getPopularMovie() { isSuccess, response ->
            dataLoading.value = false
            if (isSuccess) {
                listPopular.value = response?.results
                empty.value = false
            } else {
                empty.value = true
            }
        }
    }

    fun getTopMovie() {
        dataLoading.value = true
        repository.getTopMovie() { isSuccess, response ->
            dataLoading.value = false
            if (isSuccess) {
                listTop.value = response?.results
                empty.value = false
            } else {
                empty.value = true
            }
        }
    }

    fun getNowPlayingMovie() {
        dataLoading.value = true
        repository.getNowPlayingMovie() { isSuccess, response ->
            dataLoading.value = false
            if (isSuccess) {
                listNowPlaying.value = response?.results
                empty.value = false
            } else {
                empty.value = true
            }
        }
    }

    fun getReviews(id: Int) {
        dataLoading.value = true
        repository.getReviews(id) { isSuccess, response ->
            dataLoading.value = false
            if (isSuccess) {
                moviewReview.value = response?.results
                empty.value = false
            } else {
                empty.value = true
            }
        }
    }


}