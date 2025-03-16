package com.sunil.app.presentation.entity.movies

/**
 * Represents an item in a list of movies, which can be either a movie or a category separator.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
sealed class MovieListItem {
    /**
     * Represents a movie item in the list.
     *
     * @property id The unique identifier of the movie.
     * @property imageUrl The URL of the movie's image.
     *@property category The category to which the movie belongs.
     */
    data class Movie(
        val id: Int,
        val imageUrl: String,
        val category: String,
    ) : MovieListItem()

    /**
     * Represents a separator item in the list, used to group movies by category.
     *
     * @property category The category name for the separator.
     */
    data class Separator(val category: String) : MovieListItem()
}
