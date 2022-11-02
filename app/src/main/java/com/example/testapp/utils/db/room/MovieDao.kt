package com.example.testapp.utils.db.room

import androidx.room.*
import com.example.testapp.network.model.entity.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)  // or OnConflictStrategy.IGNORE
    fun insert(movie: MovieEntity)

    @Update
    fun update(movie: MovieEntity)

    @Query("DELETE FROM movies")
    fun deleteAll()

    @Query("DELETE FROM movies WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT * FROM movies")
    fun getAll() : List<MovieEntity>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getById(id: Int) : MovieEntity
}