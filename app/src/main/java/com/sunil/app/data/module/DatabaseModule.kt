package com.sunil.app.data.module

import android.content.Context
import androidx.room.Room
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
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    //ROOM
    private const val DATABASE_NAME = "app_db"

//    @Provides
//    @Singleton
//    fun provideAppDatabase(
//        @ApplicationContext context: Context,
//        diskExecutor: DiskExecutor
//    ): AppDatabase {
//        return Room
//            .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
//            .setQueryExecutor(diskExecutor)
//            .setTransactionExecutor(diskExecutor)
//            .fallbackToDestructiveMigration()
////        .addMigrations(*MIGRATIONS) // Apply migrations
//            .build()
//    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

    @Provides
    fun provideMovieRemoteKeyDao(appDatabase: AppDatabase): MovieRemoteKeyDao {
        return appDatabase.movieRemoteKeysDao()
    }

    @Provides
    fun provideFavoriteMovieDao(appDatabase: AppDatabase): FavoriteMovieDao {
        return appDatabase.favoriteMovieDao()
    }

}
