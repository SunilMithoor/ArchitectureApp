package com.sunil.app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sunil.app.data.local.db.dao.movies.FavoriteMovieDao
import com.sunil.app.data.local.db.dao.movies.MovieDao
import com.sunil.app.data.local.db.dao.movies.MovieRemoteKeyDao
import com.sunil.app.data.local.db.dao.user.UserDao
import com.sunil.app.data.local.db.entity.movies.FavoriteMovieDbData
import com.sunil.app.data.local.db.entity.movies.MovieDbData
import com.sunil.app.data.local.db.entity.movies.MovieRemoteKeyDbData
import com.sunil.app.data.local.db.entity.user.UserDbData


/**
 * Created by Sunil on 13/01/2025
 */
@Database(
    entities = [UserDbData::class, MovieDbData::class, FavoriteMovieDbData::class, MovieRemoteKeyDbData::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun movieDao(): MovieDao
    abstract fun movieRemoteKeysDao(): MovieRemoteKeyDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao
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
