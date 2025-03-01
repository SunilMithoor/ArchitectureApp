package com.sunil.app.data.local.db.entity.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a favorite movie entity in the local database.
 *
 * @property id The unique identifier of the movie.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
@Entity(tableName = "favorite_movies")
data class FavoriteMovieDbData(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int
)
