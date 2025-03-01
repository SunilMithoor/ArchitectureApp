package com.sunil.app.domain.entity.movies

/**
 * Represents a movie with its details.*
 * @property id The unique identifier of the movie.
 * @property title The title of the movie.
 * @property description A brief description of the movie.
 * @property imageUrl The URL of the movie's poster image.
 * @property category The category or genre of the movie.
 * @property backgroundImageUrl The URL of the movie's background image.
 */
data class MovieEntity(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val category: String,
    val backgroundImageUrl: String
)
