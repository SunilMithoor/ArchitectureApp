package com.sunil.app.presentation.entity.movies

/**
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
sealed class MovieListItem {
    data class Movie(
        val id: Int,
        val imageUrl: String,
        val category: String,
    ) : MovieListItem()

    data class Separator(val category: String) : MovieListItem()
}
