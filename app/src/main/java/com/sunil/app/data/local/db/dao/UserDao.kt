package com.sunil.app.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sunil.app.data.local.db.entity.UserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    /**
     * Saves user data into the database.*
     * @param user The UserEntity to be saved.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    /**
     * Retrieves user data from the database.
     *
     * @return A Flow emitting the UserEntity, or null if no user is found.
     */
    @Query("SELECT * FROM UserEntity LIMIT 1")
    fun getUser(): Flow<UserEntity?>

    /**
     * Deletes all user data from the database.
     */
    @Query("DELETE FROM UserEntity")
    suspend fun deleteAllUsers()
}
