package com.sunil.app.presentation.mapper.movies

import com.sunil.app.domain.entity.movies.MovieEntity
import com.sunil.app.presentation.entity.movies.MovieListItem


/**
 * @author Sunil
 * @version 1.0
 * @since 2025-02-16
 */

fun MovieEntity.toPresentation() = MovieListItem.Movie(
    id = id,
    imageUrl = image,
    category = category
)

fun MovieEntity.toMovieListItem(): MovieListItem = this.toPresentation()
