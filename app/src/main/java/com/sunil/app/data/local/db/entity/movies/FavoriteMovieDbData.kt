package com.sunil.app.data.local.db.entity.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author by Sunil on 22/01/2025
 */
@Entity(tableName = "favorite_movies")
data class FavoriteMovieDbData(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int
)
