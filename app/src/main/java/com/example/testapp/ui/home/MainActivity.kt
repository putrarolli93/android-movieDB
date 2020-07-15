package com.example.testapp.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.utils.base.BaseActivity
import com.example.testapp.utils.customview.CustomStateView
import com.example.testapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main_toolbar.*
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

open class MainActivity : BaseActivity(), DatePickerDialog.OnDateSetListener {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var popularAdapter: PopularMovieAdapter
    private lateinit var topAdapter: TopMovieAdapter
    private lateinit var nowPlayingAdapter: NowPlayingMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getStateView()?.setStateViewAction { loadingData(false) }

        headerText.text = "Movie"
        ivFavorite.setOnClickListener {
            startActivity<FavoriteActivity>()
        }

        setPopularAdapter()
        setTopAdapter()
        setNowPlayingAdapter()

        mainViewModel.getPopularMovie()
        mainViewModel.getTopMovie()
        mainViewModel.getNowPlayingMovie()
        mainViewModel.listPopular.observe(this, Observer {
            goneHorizonTopProgressBar()
            it.data?.let { it1 -> popularAdapter.updateList(it1) }
        })

        mainViewModel.listTop.observe(this, Observer {
            goneHorizonTopProgressBar()
            topAdapter.updateList(it)
        })
        mainViewModel.listNowPlaying.observe(this, Observer {
            goneHorizonTopProgressBar()
            nowPlayingAdapter.updateList(it)
//            onDataNotFound()
        })
    }

    override fun getStateView(): CustomStateView? = stateView

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

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        print(year.toString())
    }

    override fun loadingData(isFromSwipe: Boolean) {
        super.loadingData(isFromSwipe)

//        getStateView()?.gone()
    }

}
