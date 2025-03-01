package com.sunil.app.data.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sunil.app.data.local.db.AppDatabase
import com.sunil.app.data.local.db.dao.movies.FavoriteMovieDao
import com.sunil.app.data.local.db.dao.movies.MovieDao
import com.sunil.app.data.local.db.dao.movies.MovieRemoteKeyDao
import com.sunil.app.data.local.db.dao.user.UserDao
import com.sunil.app.data.utils.DiskExecutor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

/**
 * DatabaseModule: Provides dependencies related to database operations using Room.
 *
 * This module configures and provides instances of Room database, DAOs, and other
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // ROOM
    private const val DATABASE_NAME = "app_db"
    private const val TAG = "DatabaseModule"

    /**
     * Provides the AppDatabase instance.
     *
     * @param context The application context.
     * @param diskExecutor The disk executor for database operations.
     * @return The AppDatabase instance.
     */
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        diskExecutor: DiskExecutor
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .setQueryExecutor(diskExecutor)
            .setTransactionExecutor(diskExecutor)
//            .fallbackToDestructiveMigrationOnDowngrade() // Use this for downgrades
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // Perform any operations on database creation here
                    Timber.tag(TAG).d("Database created")
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    // Perform any operations on database open here
                    Timber.tag(TAG).d("Database opened")
                }
            })
            // .addMigrations(*MIGRATIONS) // Apply migrations when needed
            .build()
    }

    /**
     * Provides the UserDao instance.
     *
     * @param appDatabase The AppDatabase instance.
     * @return The UserDao instance.
     */
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    /**
     * Provides the MovieDao instance.
     *
     * @param appDatabase The AppDatabase instance.
     * @return The MovieDao instance.
     */
    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

    /**
     * Provides the MovieRemoteKeyDao instance.
     *
     * @param appDatabase The AppDatabase instance.
     * @return The MovieRemoteKeyDao instance.
     */
    @Provides
    fun provideMovieRemoteKeyDao(appDatabase: AppDatabase): MovieRemoteKeyDao {
        return appDatabase.movieRemoteKeysDao()
    }

    /**
     * Provides the FavoriteMovieDao instance.
     *
     * @param appDatabase The AppDatabase instance.
     * @return The FavoriteMovieDao instance.
     */
    @Provides
    fun provideFavoriteMovieDao(appDatabase: AppDatabase): FavoriteMovieDao {
        return appDatabase.favoriteMovieDao()
    }
}
