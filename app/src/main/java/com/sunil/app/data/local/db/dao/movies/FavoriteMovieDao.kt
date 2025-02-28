package com.sunil.app.data.local.db.dao.movies

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sunil.app.data.local.db.entity.movies.FavoriteMovieDbData
import com.sunil.app.data.local.db.entity.movies.MovieDbData

/**
 * Data Access Object (DAO) for managing favorite movies in the local database.
 *
 * This interface provides methods to interact with the 'favorite_movies' and 'movies' tables,
 * allowing for operations such as retrieving, adding, and removing favorite movies.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
@Dao
interface FavoriteMovieDao {

    /**
     * Retrieves a paginated list of favorite movies.
     *
     * This function queries the 'movies' table and filters the results to include only movies
     * whose IDs are present in the 'favorite_movies' table.
     *
     * @return A PagingSource that emits pages of MovieDbData representing favorite movies.
     */
    @Query("SELECT m.* FROM movies AS m INNER JOIN favorite_movies AS fm ON m.id = fm.id")
//    @Query("SELECT * FROM movies where id in (SELECT id FROM favorite_movies)")
    fun getFavoriteMovies(): PagingSource<Int, MovieDbData>

    /**
     * Retrieves all favorite movies from the 'favorite_movies' table.
     *
     * @return A list of FavoriteMovieDbData representing all favorite movies.
     */
    @Query("SELECT * FROM favorite_movies")
    suspend fun getAllFavoriteMovies(): List<FavoriteMovieDbData>

    /**
     * Retrieves a specific favorite movie by its ID.
     *
     * @param id The ID of the favorite movie to retrieve.
     * @return The FavoriteMovieDbData with the specified ID, or null if not found.
     */
    @Query("SELECT * FROM favorite_movies WHERE id = :id")
    suspend fun getFavoriteMovieById(id: Int): FavoriteMovieDbData?

    /**
     * Adds a movie to the list of favorites.
     *
     * If a movie with the same ID already exists, it will be replaced.
     *
     * @param favoriteMovieDbData The FavoriteMovieDbData to add.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteMovie(favoriteMovieDbData: FavoriteMovieDbData)

    /**
     * Removes a movie from the list of favorites by its ID.
     *
     * @param id The ID of the favorite movie to remove.
     */
    @Query("DELETE FROM favorite_movies WHERE id = :id")
    suspend fun removeFavoriteMovieById(id: Int)

    /**
     * Removes a movie from the list of favorites.
     *
     * @param favoriteMovieDbData The FavoriteMovieDbData to remove.
     */
    @Delete
    suspend fun removeFavoriteMovie(favoriteMovieDbData: FavoriteMovieDbData)
}
