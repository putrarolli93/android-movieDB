package com.example.testapp.utils.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.testapp.R

fun ImageView.loadFromUrl(imageUrl: String?) {
    val options = RequestOptions()
        .dontAnimate()
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .priority(Priority.IMMEDIATE)
        .placeholder(resources.getDrawable(R.drawable.placeholder_image))
    Glide.with(this.context)
        .load(imageUrl ?: "")
        .apply(options)
        .centerCrop()
        .into(this)
}