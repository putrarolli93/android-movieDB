package com.example.testapp.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieReviewModel(val results: MutableList<Reviews>) : Parcelable

@Parcelize
data class Reviews(
    val author: String,
    val content: String,
    val id: String,
    val url: String
) : Parcelable