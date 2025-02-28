package com.sunil.app.data.local.db.entity.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sunil.app.domain.entity.movies.MovieEntity


/**
 * @author by Sunil on 22/01/2025
 */
@Entity(tableName = "movies")
data class MovieDbData(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "backgroundUrl")
    val backgroundUrl: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "category")
    val category: String
)

fun MovieDbData.toDomain() = MovieEntity(
    id = id,
    title = title,
    description = description,
    image = image,
    category = category,
    backgroundUrl = backgroundUrl
)
