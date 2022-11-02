package com.example.testapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.network.model.Status
import com.example.testapp.utils.base.BaseActivity
import com.example.testapp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

open class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate){

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var popularAdapter: PopularMovieAdapter
    private lateinit var topAdapter: TopMovieAdapter
    private lateinit var nowPlayingAdapter: NowPlayingMovieAdapter

    override fun observeData() {
        super.observeData()
        observeWithCoroutine()
        observeWithRX()
    }

    private fun observeWithCoroutine() {
        mainViewModel.listPopular.observe(this) {
            parseObserveData(it,
                resultSuccess = {
                    dismissLoadingDialog()
                    it?.let { it1 -> popularAdapter.updateList(it1.results) }
                },
                resultError = {
                    dismissLoadingDialog()
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                },
                resultNetworkFailed = {
                    dismissLoadingDialog()
                    Toast.makeText(this, it?.cause?.message, Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    private fun observeWithRX() {
        mainViewModel.listTop.observe(this) {
            dismissLoadingDialog()
            when (it.status) {
                Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        topAdapter.updateList(it.data)
                    }
                }
                Status.NETWORK_FAILED -> {
                    Toast.makeText(this, it.throwable?.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun loadingData() {
        super.loadingData()
        mainViewModel.getPopularMovie()
        mainViewModel.getTopMovie()
    }

    override fun initEvent() {
        super.initEvent()
        binding.toolbarMain.ivFavorite.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding.toolbarMain.headerText.text = "Movie"

        setPopularAdapter()
        setTopAdapter()
//        setNowPlayingAdapter()
    }


    private fun setPopularAdapter() {
        popularAdapter = PopularMovieAdapter()
        val layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopular.layoutManager = layoutManager
        binding.rvPopular.adapter = popularAdapter
    }

    private fun setTopAdapter() {
        topAdapter = TopMovieAdapter()
        val layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTop.layoutManager = layoutManager
        binding.rvTop.adapter = topAdapter
    }
//
//    private fun setNowPlayingAdapter() {
//        nowPlayingAdapter = NowPlayingMovieAdapter()
//        val layoutManager =
//            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
//        binding.rvNp.layoutManager = layoutManager
//        binding.rvNp.adapter = nowPlayingAdapter
//    }

}
