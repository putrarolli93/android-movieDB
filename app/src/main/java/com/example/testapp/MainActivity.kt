package com.example.testapp

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.ui.home.NowPlayingMovieAdapter
import com.example.testapp.ui.home.PopularMovieAdapter
import com.example.testapp.ui.home.TopMovieAdapter
import com.example.testapp.utils.base.BaseActivity
import com.example.testapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var popularAdapter: PopularMovieAdapter
    private lateinit var topAdapter: TopMovieAdapter
    private lateinit var nowPlayingAdapter: NowPlayingMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        headerText.text = "Movie"
        ivFavorite.setOnClickListener {

        }
        setPopularAdapter()
        setTopAdapter()
        setNowPlayingAdapter()

        mainViewModel.getPopularMovie()
        mainViewModel.getTopMovie()
        mainViewModel.getNowPlayingMovie()
        mainViewModel.listPopular.observe(this, Observer {
            goneHorizonTopProgressBar()
            popularAdapter.updateList(it)
        })

        mainViewModel.listTop.observe(this, Observer {
            goneHorizonTopProgressBar()
            topAdapter.updateList(it)
        })
        mainViewModel.listNowPlaying.observe(this, Observer {
            goneHorizonTopProgressBar()
            nowPlayingAdapter.updateList(it)
        })
    }

    private fun setPopularAdapter() {
        popularAdapter = PopularMovieAdapter()
        val layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        rvPopular.layoutManager = layoutManager
        rvPopular.adapter = popularAdapter
    }

    private fun setTopAdapter() {
        topAdapter = TopMovieAdapter()
        val layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        rvTop.layoutManager = layoutManager
        rvTop.adapter = topAdapter
    }

    private fun setNowPlayingAdapter() {
        nowPlayingAdapter = NowPlayingMovieAdapter()
        val layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        rvNp.layoutManager = layoutManager
        rvNp.adapter = nowPlayingAdapter
    }

}
