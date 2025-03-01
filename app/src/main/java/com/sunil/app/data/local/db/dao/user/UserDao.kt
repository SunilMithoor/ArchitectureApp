package com.sunil.app.data.local.db.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sunil.app.data.local.db.entity.user.UserDbData
import kotlinx.coroutines.flow.Flow


/**
 * DAO (Data Access Object) for interacting with the 'users' table in the local database.
 *
 * This interface provides methods for managing user data, including inserting, retrieving,
 * and deleting user information.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
@Dao
interface UserDao {

    /**
     * Inserts or replaces user data in the database.
     *
     * If a user with the same primary key already exists, the existing data will be replaced.
     *
     * @param user The [UserDbData] to be inserted or replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceUser(user: UserDbData)

    /**
     * Retrieves user data from the database as a Flow.
     *
     * This method returns a [Flow] that emits the [UserDbData] whenever the data in the
     * 'users' table changes. If no user is found, the Flow will emit null.
     *
     * @return A [Flow] emitting the [UserDbData], or null if no user is found.
     */
    @Query("SELECT * FROM users LIMIT 1")
    fun getUser(): Flow<UserDbData?>

    /**
     * Deletes all user data from the database.
     *
     * This method removes all rows from the 'users' table.
     */
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}
