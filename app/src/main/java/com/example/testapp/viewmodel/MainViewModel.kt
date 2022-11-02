package com.example.testapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testapp.network.model.Movie
import com.example.testapp.network.model.MovieListModel
import com.example.testapp.network.model.Resource
import com.example.testapp.network.model.Reviews
import com.example.testapp.network.ApiService
import com.example.testapp.network.Repository
import com.example.testapp.network.repository.MainRepository
import com.example.testapp.viewmodel.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class MainViewModel(
    private val service: ApiService,
    private val repository: Repository,
    private val mainRepository: MainRepository
) : BaseViewModel() {
    val listPopular = MutableLiveData<Resource<MovieListModel>>()
    val listTop = MutableLiveData<Resource<MutableList<Movie>>>()
    val listNowPlaying = MutableLiveData<MutableList<Movie>>()
    val moviewReview = MutableLiveData<MutableList<Reviews>>()

    fun getPopularMovie() {
        viewModelScope.async(IO) {
            try {
                val result = mainRepository.getPopularMovies() //suspend
                if (result != null) {
                    listPopular.postValue(Resource.success(result))
                }
            }catch (e: Exception) {
                listPopular.postValue(Resource.networkFailed(throwable = e.cause))
            }
        }
    }

    fun getTopMovie() {
        dataLoading.value = true
        mainRepository.getTopMovie()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                listTop.postValue(Resource.success(result.results))
            }, {
                listTop.postValue(Resource.networkFailed(throwable = it))
            })
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