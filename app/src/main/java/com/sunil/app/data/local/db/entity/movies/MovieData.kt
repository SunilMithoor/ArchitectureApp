package com.sunil.app.data.local.db.entity.movies

import com.google.gson.annotations.SerializedName
import com.sunil.app.domain.entity.movies.MovieEntity

/**
 * @author by Sunil on 22/01/2025
 */
data class MovieData(
    @SerializedName("id") val id: Int,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
    @SerializedName("backgroundUrl") val backgroundUrl: String,
    @SerializedName("title") val title: String,
    @SerializedName("category") val category: String,
)

fun MovieData.toDomain() = MovieEntity(
    id = id,
    image = image,
    backgroundUrl = backgroundUrl,
    description = description,
    title = title,
    category = category
)

fun MovieData.toDbData() = MovieDbData(
    id = id,
    image = image,
    description = description,
    title = title,
    category = category,
    backgroundUrl = backgroundUrl
)
