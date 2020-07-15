package com.example.testapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.model.Movie
import com.example.testapp.utils.Constants
import com.example.testapp.utils.ext.loadFromUrl
import kotlinx.android.synthetic.main.layout_item_movie.view.*
import kotlinx.android.synthetic.main.layout_item_movie_banner.view.imgMovie

class TopMovieAdapter : RecyclerView.Adapter<TopMovieViewHolder>() {

    private var movie = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_movie, parent, false)
        return TopMovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movie.count()
    }

    override fun onBindViewHolder(holder: TopMovieViewHolder, position: Int) {
        holder.bind(movie[position])
    }

    fun updateList(list: MutableList<Movie> ) {
        this.movie = list
        this.notifyDataSetChanged()
    }

}

class TopMovieViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    fun bind(item: Movie) {
        view.apply {
            tvTitle.text = item.title
            tvRelease.text = "Release: " + item.release_date
            imgMovie.loadFromUrl(Constants.IMAGE_PATH + item.poster_path)
        }
    }

}