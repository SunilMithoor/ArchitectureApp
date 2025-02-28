package com.sunil.app.data.local.db.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sunil.app.data.local.db.entity.user.UserDbData
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    /**
     * Saves user data into the database.*
     * @param user The UserDbData to be saved.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDbData)

    /**
     * Retrieves user data from the database.
     *
     * @return A Flow emitting the UserDbData, or null if no user is found.
     */
    @Query("SELECT * FROM users LIMIT 1")
    fun getUser(): Flow<UserDbData?>

    /**
     * Deletes all user data from the database.
     */
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}
