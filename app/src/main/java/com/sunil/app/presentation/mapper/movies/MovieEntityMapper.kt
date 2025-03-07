package com.sunil.app.presentation.mapper.movies

import com.sunil.app.domain.entity.movies.MovieEntity
import com.sunil.app.presentation.entity.movies.MovieListItem


/**
 * Mapper functions to transform MovieEntity (domain layer) to MovieListItem (presentation layer).
 *
 * Extension function to convert a MovieEntity to a MovieListItem.Movie.
 *
 * This function directly maps the fields from MovieEntity to MovieListItem.Movie.
 *
 * @return A MovieListItem.Movie instance representing the converted MovieEntity.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-02-16
 */

fun MovieEntity.toPresentation() = MovieListItem.Movie(
    id = id,
    imageUrl = imageUrl,
    category = category
)


/**
 * Extension function to convert a list of MovieEntity to a list of MovieListItem.Movie.
 *
 *This function efficiently maps a collection of MovieEntity objects to a collection of MovieListItem.Movie objects.
 *
 * @return A List of MovieListItem.Movie instances representing the converted MovieEntity list.
 */
fun MovieEntity.toMovieListItem(): MovieListItem = this.toPresentation()
