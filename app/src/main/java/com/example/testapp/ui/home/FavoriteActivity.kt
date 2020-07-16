package com.example.testapp.ui.home

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.utils.db.room.MovieDao
import com.example.testapp.utils.db.room.MovieRoomDatabase
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity: AppCompatActivity() {

    private lateinit var database: MovieRoomDatabase
    private lateinit var dao: MovieDao
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.title  = "Favorite"

        setFavoriteAdapter()
        getData()
    }

    private fun getData() {
        database = MovieRoomDatabase.getDatabase(this)
        dao = database.getMovieDao()
        val favoriteMovie = dao.getAll()
        if (favoriteMovie.isNotEmpty()) {
            favoriteAdapter.updateList(favoriteMovie.toMutableList())
        }
    }

    private fun setFavoriteAdapter() {
        favoriteAdapter = FavoriteAdapter()
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvFavorite.layoutManager = layoutManager
        rvFavorite.adapter = favoriteAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}