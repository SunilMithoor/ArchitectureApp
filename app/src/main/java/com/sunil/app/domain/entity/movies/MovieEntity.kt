package com.sunil.app.domain.entity.movies

/**
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
data class MovieEntity(
    val id: Int,
    val title: String,
    val description: String,
    val image: String,
    val category: String,
    val backgroundUrl: String
)
