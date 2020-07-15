package com.example.testapp.utils.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadFromUrl(imageUrl: String?) {
    val options = RequestOptions()
        .dontAnimate()
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .priority(Priority.IMMEDIATE)
    Glide.with(this.context)
        .load(imageUrl ?: "")
        .apply(options)
        .centerCrop()
        .into(this)
}