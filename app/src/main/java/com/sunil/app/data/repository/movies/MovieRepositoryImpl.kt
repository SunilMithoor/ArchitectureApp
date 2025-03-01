package com.sunil.app.data.repository.movies


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sunil.app.data.local.db.entity.movies.toDomain
import com.sunil.app.data.remote.retrofit.datasource.movies.FavoriteMoviesDataSource
import com.sunil.app.data.remote.retrofit.datasource.movies.MovieDataSource
import com.sunil.app.data.remote.retrofit.datasource.movies.MovieRemoteMediator
import com.sunil.app.data.remote.retrofit.datasource.movies.SearchMoviePagingSource
import com.sunil.app.domain.entity.movies.MovieEntity
import com.sunil.app.domain.model.map
import com.sunil.app.domain.model.onError
import com.sunil.app.domain.model.onSuccess
import com.sunil.app.domain.model.Result
import com.sunil.app.domain.repository.movies.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


/**
 * Implementation of the [MovieRepository] interface.
 *
 * This class handles data operations related to movies, including fetching from remote and local sources,
 * managing favorites, and synchronizing data.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieDataSource.Remote,
    private val localDataSource: MovieDataSource.Local,
    private val movieRemoteMediator: MovieRemoteMediator,
    private val localFavoriteDataSource: FavoriteMoviesDataSource.Local
) : MovieRepository {

    /**
     * Retrieves a paginated flow of movies.
     *
     * @param pageSize The number of items to load per page.
     * @return A [Flow] of [PagingData] containing [MovieEntity] objects.
     */
    @OptIn(ExperimentalPagingApi::class)
    override fun getMovies(pageSize: Int): Flow<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false // Consider removing this as it's deprecated
        ),
        remoteMediator = movieRemoteMediator,
        pagingSourceFactory = { localDataSource.getPagedMovies() }
    ).flow.map { pagingData ->
        pagingData.map { it.toDomain() }
    }

    /**
     * Retrieves a paginated flow of favorite movies.
     *
     * @param pageSize The number of items to load per page.
     * @return A [Flow] of [PagingData] containing [MovieEntity] objects.
     */
    override fun getFavoriteMovies(pageSize: Int): Flow<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false // Consider removing this as it's deprecated
        ),
        pagingSourceFactory = { localFavoriteDataSource.favoriteMovies() }
    ).flow.map { pagingData ->
        pagingData.map { it.toDomain() }
    }

    /**
     * Searches for movies based on a query.
     *
     * @param query The search query string.
     * @param pageSize The number of items to load per page.
     * @return A [Flow] of [PagingData] containing [MovieEntity] objects.
     */
    override fun searchMovies(query: String, pageSize: Int): Flow<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false // Consider removing this as it's deprecated
        ),
        pagingSourceFactory = { SearchMoviePagingSource(query, remoteDataSource) }
    ).flow.map { pagingData ->
        pagingData.map { it.toDomain() }
    }

    /**
     * Retrieves a specific movie by its ID.
     *
     * @param movieId The ID of the movie to retrieve.
     * @return A [Result] containing the [MovieEntity] or an error.
     */
    override suspend fun getMovie(movieId: Int): Result<MovieEntity> {
        return when (val localResult = localDataSource.getMovieById(movieId)) {
            is Result.Success -> localResult
            is Result.Error -> remoteDataSource.getMovieById(movieId).map { it.toDomain() }
        }
    }

    /**
     * Checks if a movie is marked as a favorite.
     *
     * @param movieId The ID of the movie to check.
     * @return A [Result] containing a [Boolean] indicating the favorite status.
     */
    override suspend fun isMovieFavorite(movieId: Int): Result<Boolean> =
        localFavoriteDataSource.checkFavoriteStatus(movieId)

    /**
     * Adds a movie to the list of favorites.
     *
     * If the movie is not in the local data source, it fetches it from the remote data source first.
     *
     * @param movieId The ID of the movie to add to favorites.
     */
    override suspend fun addMovieToFavorites(movieId: Int) {
        localDataSource.getMovieById(movieId)
            .onSuccess {
                localFavoriteDataSource.addMovieToFavorite(movieId)
            }
            .onError {
                remoteDataSource.getMovieById(movieId).onSuccess { movie ->
                    localDataSource.saveMovies(listOf(movie))
                    localFavoriteDataSource.addMovieToFavorite(movieId)
                }
            }
    }

    /**
     * Removes a movie from the list of favorites.
     *
     * @param movieId The ID of the movie to remove from favorites.
     */
    override suspend fun removeMovieFromFavorites(movieId: Int) =
        localFavoriteDataSource.removeMovieFromFavorite(movieId)

    /**
     * Synchronizes the local data source with the remote data source.
     *
     * @return `true` if the synchronization was successful, `false` otherwise.
     */
    override suspend fun syncMovies(): Boolean {
        return when (val result = localDataSource.getAllMovies()) {
            is Result.Error -> false
            is Result.Success -> {
                val movieIds = result.data.map { it.id }
                updateLocalWithRemoteMovies(movieIds)
            }
        }
    }

    /**
     * Updates the local data source with movies fetched from the remote data source.
     *
     * @param movieIds The list of movie IDs to fetch from the remote data source.
     * @return `true` if the update was successful, `false` otherwise.
     */
    private suspend fun updateLocalWithRemoteMovies(movieIds: List<Int>): Boolean {
        return when (val remoteResult = remoteDataSource.getMoviesByIds(movieIds)) {
            is Result.Error -> false
            is Result.Success -> {
                localDataSource.saveMovies(remoteResult.data)
                true
            }
        }
    }
}

/**

 */

//class MovieRepositoryImpl @Inject constructor(
//    private val remote: MovieDataSource.Remote,
//    private val local: MovieDataSource.Local,
//    private val remoteMediator: MovieRemoteMediator,
//    private val localFavorite: FavoriteMoviesDataSource.Local
//) : MovieRepository {
//
//    @OptIn(ExperimentalPagingApi::class)
//    override fun movies(pageSize: Int): Flow<PagingData<MovieEntity>> = Pager(
//        config = PagingConfig(
//            pageSize = pageSize,
//            enablePlaceholders = false
//        ),
//        remoteMediator = remoteMediator,
//        pagingSourceFactory = { local.getPagedMovies() }
//    ).flow.map { pagingData ->
//        pagingData.map { it.toDomain() }
//    }
//
//    override fun favoriteMovies(pageSize: Int): Flow<PagingData<MovieEntity>> = Pager(
//        config = PagingConfig(
//            pageSize = pageSize,
//            enablePlaceholders = false
//        ),
//        pagingSourceFactory = { localFavorite.favoriteMovies() }
//    ).flow.map { pagingData ->
//        pagingData.map { it.toDomain() }
//    }
//
//    override fun search(query: String, pageSize: Int): Flow<PagingData<MovieEntity>> = Pager(
//        config = PagingConfig(
//            pageSize = pageSize,
//            enablePlaceholders = false
//        ),
//        pagingSourceFactory = { SearchMoviePagingSource(query, remote) }
//    ).flow.map { pagingData ->
//        pagingData.map { it.toDomain() }
//    }
//
//    override suspend fun getMovie(id: Int): Result<MovieEntity> {
//        return when (val localResult = local.getMovieById(id)) {
//            is Result.Success -> localResult
//            is Result.Error -> remote.getMovieById(id).map { it.toDomain() }
//        }
//    }
//
//    override suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean> =
//        localFavorite.checkFavoriteStatus(movieId)
//
//    override suspend fun addMovieToFavorite(movieId: Int) {
//        local.getMovieById(movieId)
//            .onSuccess {
//                localFavorite.addMovieToFavorite(movieId)
//            }
//            .onError {
//                remote.getMovieById(movieId).onSuccess { movie ->
//                    local.saveMovies(listOf(movie))
//                    localFavorite.addMovieToFavorite(movieId)
//                }
//            }
//    }
//
//    override suspend fun removeMovieFromFavorite(movieId: Int) =
//        localFavorite.removeMovieFromFavorite(movieId)
//
//    override suspend fun sync(): Boolean {
//        return when (val result = local.getAllMovies()) {
//            is Result.Error -> false
//            is Result.Success -> {
//                val movieIds = result.data.map { it.id }
//                return updateLocalWithRemoteMovies(movieIds)
//            }
//        }
//    }
//
//    private suspend fun updateLocalWithRemoteMovies(movieIds: List<Int>): Boolean {
//        return when (val remoteResult = remote.getMoviesByIds(movieIds)) {
//            is Result.Error -> false
//            is Result.Success -> {
//                local.saveMovies(remoteResult.data)
//                true
//            }
//        }
//    }
//}
