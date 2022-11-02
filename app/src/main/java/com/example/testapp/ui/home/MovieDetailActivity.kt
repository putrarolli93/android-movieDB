package com.example.testapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.testapp.R
import com.example.testapp.databinding.ActivityDetailMovieBinding
import com.example.testapp.databinding.LayoutItemReviewBinding
import com.example.testapp.network.model.Movie
import com.example.testapp.network.model.entity.MovieEntity
import com.example.testapp.utils.Constants
import com.example.testapp.utils.base.BaseActivity
import com.example.testapp.utils.db.room.MovieDao
import com.example.testapp.utils.db.room.MovieRoomDatabase
import com.example.testapp.utils.ext.loadFromUrl
import com.example.testapp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDetailActivity : BaseActivity<ActivityDetailMovieBinding>(ActivityDetailMovieBinding::inflate) {

    private var movie: Movie? = null
    private var movieDB: MovieEntity? = null
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var database: MovieRoomDatabase
    private lateinit var dao: MovieDao
    private var isFavorite: Boolean = false
    private var movieId: Int = 0

    override fun initData() {
        super.initData()
        database = MovieRoomDatabase.getDatabase(this)
        dao = database.getMovieDao()
        getIntentData()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setToolbar()
    }

    override fun loadingData() {
        super.loadingData()
        getMovieData()
    }

    private fun getIntentData() {
        movie = intent?.extras?.getParcelable("item")
        movieDB = intent?.getParcelableExtra("movieDB")
        if (movie != null) {
            movie?.let {
                movieId = it.id
                setView(it)
                mainViewModel.getReviews(it.id)
            }
        }
        if (movieDB != null) {
            movieDB?.let {
                movieId = it.id
                setView(Movie(it.id, it.posterPath, it.title, it.releaseDate, it.overview))
                mainViewModel.getReviews(it.id)
            }
        }
        mainViewModel.moviewReview.observe(this, Observer {
            dismissLoadingDialog()
            for (i in 0 until it.count()) {
                val view = LayoutItemReviewBinding.inflate(LayoutInflater.from(this))
                binding.llReview.addView(view.clReview)
                view.tvAuthor.text = "author : " + it[i].author
                view.tvReview.text = it[i].content
            }
        })
    }

    private fun setToolbar() {
        binding.toolbar.setOnClickListener {
            finish()
        }
        binding.collapsingToolbar.title = "Detail"
        binding.collapsingToolbar.setCollapsedTitleTextColor(
            ContextCompat.getColor(
                this,
                R.color.colorWhite
            )
        )
        binding.collapsingToolbar.setExpandedTitleColor(
            ContextCompat.getColor(
                this,
                android.R.color.transparent
            )
        )
    }

    private fun setView(item: Movie) {
        binding.apply {
            imgMovie.loadFromUrl(Constants.IMAGE_PATH + item.poster_path)
            tvTitle.text = item.title
            tvReleaseDate.text = item.release_date
            tvDescription.text = item.overview
            ivShare.setOnClickListener {
                shareMovie(item)
            }
            ivLove.setOnClickListener {
                var isVaforiteMovie = if (isFavorite) false else true
                if (isVaforiteMovie) {
                    dao.insert(
                        MovieEntity(
                            item.id,
                            item.poster_path,
                            item.title,
                            item.release_date,
                            item.overview,
                            isVaforiteMovie
                        )
                    )
                } else {
                    dao.deleteById(item.id)
                }
                getMovieData()
            }
        }
    }

    private fun shareMovie(item: Movie) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Good Movie")
        var shareMessage =
            "\ni'd like to share my favorite movie i hope you like this movie. :)\n"
        var title = "\n This is -- ${item.title} --\n"
        shareMessage =
            "$shareMessage $title ${Constants.IMAGE_PATH + item.poster_path} \n Terima Kasih"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "choose one"))
    }

    private fun getMovieData() {
        val movieById = dao.getById(movieId)
        if (movieById != null) {
            isFavorite = movieById?.isFavorite ?: false
        } else {
            isFavorite = false
        }
        if (this.isFavorite) {
            binding.ivLove.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.heart))
        } else {
            binding.ivLove.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.heart_empty))
        }
    }

}