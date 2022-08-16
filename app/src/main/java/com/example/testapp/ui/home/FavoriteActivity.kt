package com.example.testapp.ui.home

import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.databinding.ActivityFavoriteBinding
import com.example.testapp.utils.base.BaseActivity
import com.example.testapp.utils.db.room.MovieDao
import com.example.testapp.utils.db.room.MovieRoomDatabase

class FavoriteActivity : BaseActivity<ActivityFavoriteBinding>(ActivityFavoriteBinding::inflate) {

    private lateinit var database: MovieRoomDatabase
    private lateinit var dao: MovieDao
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.title = "Favorite"

        setFavoriteAdapter()
        getData()
    }

    private fun getData() {
        database = MovieRoomDatabase.getDatabase(this)
        dao = database.getMovieDao()
        val favoriteMovie = dao.getAll()
        favoriteAdapter.updateList(favoriteMovie.toMutableList())
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun setFavoriteAdapter() {
        favoriteAdapter = FavoriteAdapter()
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding?.rvFavorite.layoutManager = layoutManager
        binding?.rvFavorite.adapter = favoriteAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}