package com.example.testapp.utils.di

import com.example.testapp.network.Repository
import com.example.testapp.network.repository.MainRepository
import com.example.testapp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MainViewModel(get(), get(), get())
    }

    factory {
        Repository(get())
    }

    single {
        MainRepository(get())
    }

}