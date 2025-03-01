package com.sunil.app.data.local.db.entity.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Represents the remote keys for movie pagination in the local database.
 *
 * This entity is used to store the previous and next page keys for each movie,
 * enabling efficient pagination of movie data fetched from a remote source.
 *
 * @property remoteKeyId The primary key for the remote key entry. Auto-generated.
 * @property movieId The ID of the associated movie. This is a foreign key referencing [MovieDbData].
 * @property prevPage The key for the previous page of movies, or null if this is the first page.
 * @property nextPage The key for the next page of movies, or null if this is the last page.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
//@Entity(
//    tableName = "movies_remote_keys",
//    foreignKeys = [
//        ForeignKey(
//            entity = MovieDbData::class,
//            parentColumns = ["id"],
//            childColumns = ["movie_id"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
//)
@Entity(    tableName = "movies_remote_keys")
data class MovieRemoteKeyDbData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val remoteKeyId: Int = 0,

//    @ColumnInfo(name = "movie_id", index = true)
//    val movieId: Int,

    @ColumnInfo(name = "prev_page")
    val prevPage: Int?,

    @ColumnInfo(name = "next_page")
    val nextPage: Int?,
)
