package com.sunil.app.data.local.db.entity.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a user entity in the local database.
 *
 * This data class defines the structure of the 'users' table in the database.
 * It includes the user's unique ID, email, and a hashed password.
 * @property userId The unique identifier for the user. Auto-generated by the database.
 * @property email The user's email address.
 * @property passwordHash A hashed version of the user's password for security.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
@Entity(tableName = "users")
data class UserDbData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: Int = 0,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "password_hash")
    val passwordHash: String
)
