package com.example.testapp.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.LayoutItemFavoriteBinding
import com.example.testapp.model.entity.MovieEntity
import com.example.testapp.utils.Constants
import com.example.testapp.utils.ext.loadFromUrl

class FavoriteAdapter : RecyclerView.Adapter<FavoriteViewHolder>() {

    private var movie = mutableListOf<MovieEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

class FavoriteViewHolder(private val view: LayoutItemFavoriteBinding): RecyclerView.ViewHolder(view.root) {

    fun bind(item: MovieEntity) {
        view.apply {
            tvTitle.text = item.title
            tvRelease.text = "Release: " + item.releaseDate
            imgMovie.loadFromUrl(Constants.IMAGE_PATH + item.posterPath)
            imgMovie.setOnClickListener {
                val intent = Intent(itemView.context, MovieDetailActivity::class.java)
                intent.putExtra("movieDB", item)
                itemView.context.startActivity(intent)
            }
            tvOverview.text = item.overview
        }
    }

}