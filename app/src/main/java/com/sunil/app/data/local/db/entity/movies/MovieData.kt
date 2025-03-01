package com.sunil.app.data.local.db.entity.movies

import com.google.gson.annotations.SerializedName
import com.sunil.app.domain.entity.movies.MovieEntity


/**
 * Represents a movie data object received from an external source (e.g., API).
 *
 * @property id The unique identifier of the movie.
 * @property description A brief description of the movie.
 * @property imageUrl The URL of the movie's poster image.
 * @property backgroundImageUrl The URL of the movie's background image.
 * @property title The title of the movie.
 * @property category The category or genre of the movie.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
data class MovieData(
    @SerializedName("id") val id: Int,
    @SerializedName("description") val description: String,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("backgroundUrl") val backgroundImageUrl: String,
    @SerializedName("title") val title: String,
    @SerializedName("category") val category: String,
)

/**
 * Extension function to convert a [MovieData] object to a [MovieEntity] object.
 *
 * @return A [MovieEntity] object representing the movie data.
 */
fun MovieData.toDomain(): MovieEntity = MovieEntity(
    id = id,
    imageUrl = imageUrl,
    backgroundImageUrl = backgroundImageUrl,
    description = description,
    title = title,
    category = category
)

/**
 * Extension function to convert a [MovieData] object to a [MovieDbData] object.
 *
 * @return A [MovieDbData] object representing the movie data for database storage.
 */
fun MovieData.toDbData(): MovieDbData = MovieDbData(
    id = id,
    imageUrl = imageUrl,
    description = description,
    title = title,
    category = category,
    backgroundImageUrl = backgroundImageUrl
)
