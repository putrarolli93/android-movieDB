package com.example.testapp.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movies")
@Parcelize
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "poster_path") var posterPath: String = "",
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "release_date") var releaseDate: String = "",
    @ColumnInfo(name = "overview") var overview: String = "",
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false
) : Parcelable