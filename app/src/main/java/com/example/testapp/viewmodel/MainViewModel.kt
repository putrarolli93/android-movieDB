package com.example.testapp.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.example.testapp.model.Movie
import com.example.testapp.model.Resource
import com.example.testapp.model.Reviews
import com.example.testapp.network.ApiService
import com.example.testapp.network.Repository
import com.example.testapp.viewmodel.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val service: ApiService, private val repository: Repository) : BaseViewModel() {
    val listPopular = MutableLiveData<Resource<MutableList<Movie>>>()
    val listTop = MutableLiveData<MutableList<Movie>>()
    val listNowPlaying = MutableLiveData<MutableList<Movie>>()
    val moviewReview = MutableLiveData<MutableList<Reviews>>()

    @SuppressLint("CheckResult")
    fun getPopularMovie() {
        dataLoading.value = true
        service.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                listPopular.value = Resource.success(result.results)
                dataLoading.value = false
            }, {
                listPopular.postValue(Resource.networkFailed(it))
                dataLoading.value = false
            })
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