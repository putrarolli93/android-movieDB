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
import kotlinx.android.synthetic.main.layout_item_movie_banner.view.*
import kotlinx.android.synthetic.main.layout_item_movie_banner.view.imgMovie
import org.jetbrains.anko.intentFor

class PopularMovieAdapter : RecyclerView.Adapter<PopularMovieViewHolder>() {

    private var movie = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_movie_banner, parent, false)
        return PopularMovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movie.count()
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        holder.bind(movie[position])
    }

    fun updateList(list: MutableList<Movie> ) {
        this.movie = list
        this.notifyDataSetChanged()
    }

}

class PopularMovieViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    fun bind(item: Movie) {
        view.apply {
            imgMovie.loadFromUrl(Constants.IMAGE_PATH + item.poster_path)
            imgMovie.setOnClickListener {
                context.startActivity(context.intentFor<MovieDetailActivity>(
                    "item" to item
                ))
            }
        }
    }

}