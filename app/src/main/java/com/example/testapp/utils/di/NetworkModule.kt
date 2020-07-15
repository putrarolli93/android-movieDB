package com.example.testapp.utils.di

import com.example.testapp.network.ApiClient
import com.example.testapp.network.Repository
import org.koin.dsl.module

val networkModule = module {

    single {
        ApiClient()
    }

    factory {
        Repository(get())
    }

}
