package com.example.testapp.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.LayoutItemMovieBannerBinding
import com.example.testapp.network.model.Movie
import com.example.testapp.utils.Constants
import com.example.testapp.utils.ext.loadFromUrl

class PopularMovieAdapter : RecyclerView.Adapter<PopularMovieViewHolder>() {

    private var movie = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        val view = LayoutItemMovieBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

class PopularMovieViewHolder(private val view: LayoutItemMovieBannerBinding): RecyclerView.ViewHolder(view.root) {

    fun bind(item: Movie) {
        view.apply {
            imgMovie.loadFromUrl(Constants.IMAGE_PATH + item.poster_path)
            imgMovie.setOnClickListener {
                val intent = Intent(itemView.context, MovieDetailActivity::class.java)
                intent.putExtra("item", item)
                itemView.context.startActivity(intent)
            }
        }
    }

}