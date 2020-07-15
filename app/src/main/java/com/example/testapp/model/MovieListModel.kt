package com.example.testapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieListModel(val results: MutableList<Movie>) : Parcelable

@Parcelize
data class Movie(
    val poster_path: String,
    val title: String,
    val release_date: String,
    val overview: String
) : Parcelable