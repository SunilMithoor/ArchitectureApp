package com.sunil.app.data.local.db.dao.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sunil.app.data.local.db.entity.movies.MovieRemoteKeyDbData

/**
 * DAO (Data Access Object) for managing remote keys related to movies.
 *
 *This interface defines methods for interacting with the 'movies_remote_keys' table in the local database.
 * It provides functionalities to save, retrieve, delete, and find the last remote key.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
@Dao
interface MovieRemoteKeyDao {

    /**
     * Saves a remote key to the database.
     *
     * If a remote key with the same ID already exists, it will be replaced.
     *
     * @param remoteKey The remote key data to be saved.*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKey(remoteKey: MovieRemoteKeyDbData)

    /**
     * Retrieves a remote key by its associated movie ID.
     *
     * @param movieId The ID of the movie associated with the remote key.
     * @return The remote key data if found, null otherwise.
     */
//    @Query("SELECT * FROM movies_remote_keys WHERE movieId = :movieId")
    @Query("SELECT * FROM movies_remote_keys WHERE id=:movieId")
    suspend fun getRemoteKeyByMovieId(movieId: Int): MovieRemoteKeyDbData?

    /**
     * Deletes all remote keys from the database.
     */
    @Query("DELETE FROM movies_remote_keys")
    suspend fun clearRemoteKeys()

    /**
     * Retrieves the last remote key inserted into the database.
     *
     * This is determined by finding the remote key with the maximum 'id'.
     *
     * @return The last remote key data if found, null otherwise.
     */
//    @Query("SELECT * FROM movies_remote_keys ORDER BY id DESC LIMIT 1")
    @Query("SELECT * FROM movies_remote_keys WHERE id = (SELECT MAX(id) FROM movies_remote_keys)")
    suspend fun getLastRemoteKey(): MovieRemoteKeyDbData?
}
