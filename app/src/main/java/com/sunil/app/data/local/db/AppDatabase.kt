package com.sunil.app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sunil.app.data.local.db.AppDatabase.Companion.DB_VERSION
import com.sunil.app.data.local.db.dao.movies.FavoriteMovieDao
import com.sunil.app.data.local.db.dao.movies.MovieDao
import com.sunil.app.data.local.db.dao.movies.MovieRemoteKeyDao
import com.sunil.app.data.local.db.dao.user.UserDao
import com.sunil.app.data.local.db.entity.movies.FavoriteMovieDbData
import com.sunil.app.data.local.db.entity.movies.MovieDbData
import com.sunil.app.data.local.db.entity.movies.MovieRemoteKeyDbData
import com.sunil.app.data.local.db.entity.user.UserDbData


/**
 * AppDatabase: Defines the Room database for the application.
 *
 * This class serves as the main access point for the persisted data.
 * It defines the database schema, version, and provides access to the DAOs.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
@Database(
    entities = [UserDbData::class, MovieDbData::class, FavoriteMovieDbData::class, MovieRemoteKeyDbData::class],
    version = DB_VERSION,
    exportSchema = true, // Enable schema export for migration testing
//    autoMigrations = [
//        AutoMigration(from = 1, to = 2) // Example of an automatic migration
//    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun movieDao(): MovieDao
    abstract fun movieRemoteKeysDao(): MovieRemoteKeyDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao

    companion object {
        const val DB_VERSION = 1 // Current database version

//        // Manual migration example from version 2to 3
//        val MIGRATION_2_3 = object : Migration(2, 3) {
//            override fun migrate(db: SupportSQLiteDatabase) {
//                // Example: Add a new column called "createdAt" to the "movies" table
//                db.execSQL("ALTER TABLE movies ADD COLUMN createdAt INTEGER DEFAULT 0 NOT NULL")
//            }
//        }
//
//        // Add more migrations here as needed (e.g., MIGRATION_3_4, MIGRATION_4_5)
//        val MIGRATIONS = arrayOf(MIGRATION_2_3)
    }
}


//@Database(entities = [UserDbData::class], version = 2) // Increment version when you change the schema
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun userDao(): UserDao
//
//    companion object {
//        val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(db: SupportSQLiteDatabase) {
//                // Example: Add a new column called "age" to the "User" table
//                db.execSQL("ALTER TABLE User ADD COLUMN age INTEGER DEFAULT 0 NOT NULL")
//            }
//        }
//
//        // Add more migrations here as needed (e.g., MIGRATION_2_3, MIGRATION_3_4)
//        val MIGRATIONS = arrayOf(MIGRATION_1_2)
//    }
//}
