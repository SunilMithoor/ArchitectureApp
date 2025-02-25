package com.sunil.app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sunil.app.data.local.db.dao.UserDao
import com.sunil.app.data.local.db.entity.UserEntity


/**
 * Created by Sunil
 */

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract val userDao: UserDao
}


//@Database(entities = [UserEntity::class], version = 2) // Increment version when you change the schema
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
