package com.sunil.app.data.local.db.entity.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sunil.app.domain.entity.movies.MovieEntity


/**
 * Represents a movie entity in the local database.
 *
 * @property id The unique identifier of the movie.
 * @property description A brief description of the movie.
 * @property imageUrl The URL of the movie's poster image.
 * @property backgroundImageUrl The URL of the movie's background image.
 * @property title The title of the movie.
 * @property category The category the movie belongs to.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
@Entity(tableName = "movies")
data class MovieDbData(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "background_image_url")
    val backgroundImageUrl: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "category")
    val category: String
)

/**
 * Extension function to convert a [MovieDbData] object to a [MovieEntity] object.
 *
 * @return A [MovieEntity] object representing the movie data.
 */
fun MovieDbData.toDomain(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    description = description,
    imageUrl = imageUrl,
    category = category,
    backgroundImageUrl = backgroundImageUrl
)
