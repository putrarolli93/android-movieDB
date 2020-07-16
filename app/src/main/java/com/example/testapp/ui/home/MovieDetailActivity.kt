package com.example.testapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.testapp.R
import com.example.testapp.model.Movie
import com.example.testapp.model.entity.MovieEntity
import com.example.testapp.utils.Constants
import com.example.testapp.utils.base.BaseActivity
import com.example.testapp.utils.db.room.MovieDao
import com.example.testapp.utils.db.room.MovieRoomDatabase
import com.example.testapp.utils.ext.loadFromUrl
import com.example.testapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.layout_item_review.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDetailActivity : BaseActivity() {

    private var movie: Movie? = null
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var database: MovieRoomDatabase
    private lateinit var dao: MovieDao
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        getIntentData()
        setToolbar()
        database = MovieRoomDatabase.getDatabase(this)
        dao = database.getMovieDao()
        getMovieData()
    }

    private fun getIntentData() {
        movie = intent?.getParcelableExtra("item") as Movie
        movie?.let {
            setView(it)
            mainViewModel.getReviews(it.id)
        }
        mainViewModel.moviewReview.observe(this, Observer {
            goneHorizonTopProgressBar()
            for (i in 0 until it.count()) {
                val child = layoutInflater.inflate(R.layout.layout_item_review, clReview, false)
                llReview.addView(child)
                tvAuthor.text = "author : " + it[i].author
                tvReview.text = it[i].content
            }
        })
    }

    private fun setToolbar() {
        toolbar.setOnClickListener {
            finish()
        }
        collapsingToolbar.title = "Detail"
        collapsingToolbar.setCollapsedTitleTextColor(
            ContextCompat.getColor(
                this,
                R.color.colorWhite
            )
        )
        collapsingToolbar.setExpandedTitleColor(
            ContextCompat.getColor(
                this,
                android.R.color.transparent
            )
        )
    }

    private fun setView(item: Movie) {
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
            }else {
                dao.deleteById(item.id)
            }

            getMovieData()
        }
    }

    private fun shareMovie(item: Movie) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Good Movie")
        var shareMessage =
            "\ni'd like to share my favorite movie, i hope you like this movie. :)\n"
        shareMessage =
            "$shareMessage ${Constants.IMAGE_PATH + item.poster_path} \n Terima Kasih"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "choose one"))
    }

    private fun getMovieData() {
        val movie = movie?.id?.let {
            dao.getById(it)
        }
        isFavorite = movie?.isFavorite ?: false
        if (this.isFavorite) {
            ivLove.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.heart))
        } else {
            ivLove.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.heart_empty))
        }
    }
}