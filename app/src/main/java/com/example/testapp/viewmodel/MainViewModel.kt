package com.example.testapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testapp.model.Movie
import com.example.testapp.model.Resource
import com.example.testapp.model.Reviews
import com.example.testapp.network.ApiService
import com.example.testapp.network.Repository
import com.example.testapp.repository.MainRepository
import com.example.testapp.viewmodel.base.BaseViewModel
import kotlinx.coroutines.*

class MainViewModel(
    private val service: ApiService,
    private val repository: Repository,
    private val mainRepository: MainRepository
) : BaseViewModel() {
    val listPopular = MutableLiveData<Resource<MutableList<Movie>?>>()
    val listTop = MutableLiveData<MutableList<Movie>>()
    val listNowPlaying = MutableLiveData<MutableList<Movie>>()
    val moviewReview = MutableLiveData<MutableList<Reviews>>()

    fun getPopularMovie() {
        viewModelScope.async(Dispatchers.IO) {
            try {
                val result = mainRepository.getPopularMovies().await() //suspend
//                withContext(Dispatchers.Main) {
                    if (result.results != null) {
                        listPopular.postValue(Resource.success(result?.results))
                    }
//                }
            }catch (e: Exception) {
                listPopular.postValue(Resource.networkFailed(throwable = e.cause))
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