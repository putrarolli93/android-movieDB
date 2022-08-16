package com.example.testapp.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.LayoutItemMovieBinding
import com.example.testapp.model.Movie
import com.example.testapp.utils.Constants
import com.example.testapp.utils.ext.loadFromUrl

class NowPlayingMovieAdapter : RecyclerView.Adapter<NowPlayingMovieViewHolder>() {

    private var movie = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingMovieViewHolder {
        val view = LayoutItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

class NowPlayingMovieViewHolder(private val view: LayoutItemMovieBinding): RecyclerView.ViewHolder(view.root) {

    fun bind(item: Movie) {
        view.apply {
            tvTitle.text = item.title
            tvRelease.text = "Release: " + item.release_date
            imgMovie.loadFromUrl(Constants.IMAGE_PATH + item.poster_path)
            imgMovie.setOnClickListener {
                val intent = Intent(itemView.context, MovieDetailActivity::class.java)
                intent.putExtra("item", item)
                itemView.context.startActivity(intent)
            }
        }
    }

}