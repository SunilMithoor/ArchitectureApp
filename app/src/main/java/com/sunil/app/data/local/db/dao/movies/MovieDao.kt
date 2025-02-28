package com.sunil.app.data.local.db.dao.movies

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sunil.app.data.local.db.entity.movies.MovieDbData

/**
 * DAO (Data Access Object) for interacting with the 'movies' table in the local database.
 *
 * This interface provides methods for querying, inserting, and deleting movie data.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
@Dao
interface MovieDao {

    /**
     * Retrieves a [PagingSource] for movies, ordered by category.
     *
     * This method is used for efficient pagination of movie data.
     *
     * @return A [PagingSource] that emits pages of [MovieDbData].
     */
    @Query("SELECT * FROM movies ORDER BY category")
    fun getPagedMovies(): PagingSource<Int, MovieDbData>

    /**
     * Retrieves all movies from the 'movies' table, ordered by category.
     *
     * This method is suitable for cases where the entire list of movies is needed at once.
     *
     * @return A list of all [MovieDbData] in the database.
     */
    @Query("SELECT * FROM movies ORDER BY category")
    suspend fun getAllMovies(): List<MovieDbData>

    /**
     * Retrieves a specific movie by its ID.
     *
     * @param id The ID of the movie to retrieve.
     * @return The [MovieDbData] with the specified ID, or null if no such movie exists.
     */
    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieDbData?

    /**
     * Inserts or replaces a list of movies in the 'movies' table.
     *
     * If a movie with the same ID already exists, it will be replaced.
     *
     * @param movies The list of [MovieDbData] to insert or replace.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceMovies(movies: List<MovieDbData>)

    /**
     * Deletes all movies from the 'movies' table that are not marked as favorites.
     *
     * This method ensures that only non-favorite movies are removed.
     */
    @Query("DELETE FROM movies WHERE id NOT IN (SELECT id FROM favorite_movies)")
    suspend fun deleteNonFavoriteMovies()
}
