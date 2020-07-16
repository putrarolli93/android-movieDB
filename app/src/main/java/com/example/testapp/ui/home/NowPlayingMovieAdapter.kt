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
import kotlinx.android.synthetic.main.layout_item_movie.view.imgMovie
import org.jetbrains.anko.intentFor

class NowPlayingMovieAdapter : RecyclerView.Adapter<NowPlayingMovieViewHolder>() {

    private var movie = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingMovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_movie, parent, false)
        return NowPlayingMovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movie.count()
    }

    override fun onBindViewHolder(holder: NowPlayingMovieViewHolder, position: Int) {
        holder.bind(movie[position])
    }

    fun updateList(list: MutableList<Movie> ) {
        this.movie = list
        this.notifyDataSetChanged()
    }

}

class NowPlayingMovieViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    fun bind(item: Movie) {
        view.apply {
            tvTitle.text = item.title
            tvRelease.text = "Release: " + item.release_date
            imgMovie.loadFromUrl(Constants.IMAGE_PATH + item.poster_path)
            imgMovie.setOnClickListener {
                context.startActivity(context.intentFor<MovieDetailActivity>(
                    "item" to item
                ))
            }
        }
    }

}