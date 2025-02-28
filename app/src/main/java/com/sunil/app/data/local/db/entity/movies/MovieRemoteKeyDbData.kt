package com.sunil.app.data.local.db.entity.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author by Sunil on 22/01/2025
 */
@Entity(tableName = "movies_remote_keys")
data class MovieRemoteKeyDbData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "prev_page")
    val prevPage: Int?,
    @ColumnInfo(name = "next_page")
    val nextPage: Int?,
)
