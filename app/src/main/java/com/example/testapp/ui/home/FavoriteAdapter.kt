package com.example.testapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.model.entity.MovieEntity
import com.example.testapp.utils.Constants
import com.example.testapp.utils.ext.loadFromUrl
import kotlinx.android.synthetic.main.layout_item_favorite.view.*
import kotlinx.android.synthetic.main.layout_item_movie.view.imgMovie
import kotlinx.android.synthetic.main.layout_item_movie.view.tvRelease
import kotlinx.android.synthetic.main.layout_item_movie.view.tvTitle
import org.jetbrains.anko.intentFor

class FavoriteAdapter : RecyclerView.Adapter<FavoriteViewHolder>() {

    private var movie = mutableListOf<MovieEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movie.count()
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(movie[position])
    }

    fun updateList(list: MutableList<MovieEntity> ) {
        this.movie = list
        this.notifyDataSetChanged()
    }

}

class FavoriteViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    fun bind(item: MovieEntity) {
        view.apply {
            tvTitle.text = item.title
            tvRelease.text = "Release: " + item.releaseDate
            imgMovie.loadFromUrl(Constants.IMAGE_PATH + item.posterPath)
            imgMovie.setOnClickListener {
                context.startActivity(context.intentFor<MovieDetailActivity>(
                    "movieDB" to item
                ))
            }
            tvOverview.text = item.overview
        }
    }

}