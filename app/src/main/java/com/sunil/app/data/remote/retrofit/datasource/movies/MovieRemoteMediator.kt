package com.sunil.app.data.remote.retrofit.datasource.movies

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.sunil.app.data.local.db.entity.movies.MovieDbData
import com.sunil.app.data.local.db.entity.movies.MovieRemoteKeyDbData
import com.sunil.app.domain.model.Result
import timber.log.Timber

/**
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val local: MovieDataSource.Local,
    private val remote: MovieDataSource.Remote
) : RemoteMediator<Int, MovieDbData>() {

    companion object {
        private const val TAG = "MovieRemoteMediator"
        private const val MOVIE_STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieDbData>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> MOVIE_STARTING_PAGE_INDEX
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> local.getLastRemoteKey()?.nextPage ?: return MediatorResult.Success(
                endOfPaginationReached = true
            )
        }
        Timber.tag(TAG)
            .d("MovieRemoteMediator: load() called with: loadType = $loadType, page: $page, stateLastItem = ${state.isEmpty()}")

        // There was a lag in loading the first page; as a result, it jumps to the end of the pagination.
        if (state.isEmpty() && page == 2) return MediatorResult.Success(endOfPaginationReached = false)

        when (val result = remote.getMovies(page, state.config.pageSize)) {
            is Result.Success -> {
                Timber.tag(TAG).d("MovieRemoteMediator: get movies from remote")
                if (loadType == LoadType.REFRESH) {
                    local.clearMovies()
                    local.clearRemoteKeys()
                }

                val movies = result.data

                val endOfPaginationReached = movies.isEmpty()

                val prevPage = if (page == MOVIE_STARTING_PAGE_INDEX) null else page - 1
                val nextPage = if (endOfPaginationReached) null else page + 1

                val key = MovieRemoteKeyDbData(prevPage = prevPage, nextPage = nextPage)

                local.saveMovies(movies)
                local.saveRemoteKey(key)

                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }

            is Result.Error -> {
                return MediatorResult.Error(result.error)
            }
        }
    }
}


///**
// * Mediates between the remote (network) and local (database) data sources for movies.
// *
// * @author Sunil
// * @version 1.0
// * @since 2025-01-28
// */
//@OptIn(ExperimentalPagingApi::class)
//class MovieRemoteMediator(
//    private val local: MovieDataSource.Local,
//    private val remote: MovieDataSource.Remote) : RemoteMediator<Int, MovieDbData>() {
//
//    companion object {
//        private const val TAG = "MovieRemoteMediator"
//        private const val MOVIE_STARTING_PAGE_INDEX = 1
//    }
//
//    /**
//     * Loads data from the remote source based on the [loadType] and [state].
//     *
//     * @param loadType The type of load operation (REFRESH, PREPEND, APPEND).
//     * @param state The current paging state.
//     * @return A [MediatorResult] indicating the success or failure of the load operation.
//     */
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, MovieDbData>
//    ): MediatorResult {
//        return try {
//            // Determine the page to load based on the load type.
//            val page = when (loadType) {
//                LoadType.REFRESH -> {
//                    // For refresh, get the closest page to the anchor position or start from the beginning.
//                    val closestPage = state.anchorPosition?.let {
//                        state.closestPageToPosition(it)?.prevKey?.plus(1)
//                            ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
//                    }
//                    closestPage ?: MOVIE_STARTING_PAGE_INDEX
//                }
//
//                LoadType.PREPEND -> {
//                    // Prepend is not supported in this implementation, so return success with endOfPaginationReached.
//                    val remoteKeys = getRemoteKeyForFirstItem(state)
//                    val prevKey = remoteKeys?.prevPage
//                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                    prevKey
//                }
//
//                LoadType.APPEND -> {
//                    // For append, get the next page from the last remote key.
//                    val remoteKeys = getRemoteKeyForLastItem(state)
//                    val nextKey = remoteKeys?.nextPage
//                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                    nextKey
//                }
//            }
//
//            Timber.tag(TAG)
//                .d("MovieRemoteMediator: load() called with: loadType = $loadType, page: $page, stateLastItem = ${state.isEmpty()}")
//
//            // There was a lag in loading the first page; as a result, it jumps to the end of the pagination.
//            // This condition is no longer necessary with the new logic for LoadType.REFRESH
//            // if (state.isEmpty() && page == 2) return MediatorResult.Success(endOfPaginationReached = false)
//
//            // Fetch movies from the remote source.
//            when (val result = remote.getMovies(page, state.config.pageSize)) {
//                is Result.Success -> {
//                    Timber.tag(TAG).d("MovieRemoteMediator: get movies from remote")
//
//                    // Clear local data on refresh.
//                    if (loadType == LoadType.REFRESH) {
//                        local.clearMovies()
//                        local.clearRemoteKeys()
//                    }
//
//                    val movies = result.data
//                    val endOfPaginationReached = movies.isEmpty()
//
//                    // Calculate previous and next page keys.
//                    val prevPage = if (page == MOVIE_STARTING_PAGE_INDEX) null else page - 1
//                    val nextPage = if (endOfPaginationReached) null else page + 1
//
//                    // Create and save the remote key.
//                    val key = MovieRemoteKeyDbData(prevPage = prevPage, nextPage = nextPage)
//                    local.saveRemoteKey(key)
//
//                    // Save the fetched movies to the local database.
//                    local.saveMovies(movies)
//
//                    // Return success with the endOfPaginationReached flag.
//                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
//                }
//
//                is Result.Error -> {
//                    // Return an error result if the remote fetch fails.
//                    MediatorResult.Error(result.error)
//                }
//            }
//        } catch (e: IOException) {
//            MediatorResult.Error(e)
//        } catch (e: Exception) {
//            MediatorResult.Error(e)
//        }
//    }
//
//    /**
//     * Gets the remote key for the first item in the current state.
//     *
//     * @param state The current paging state.
//     * @return The remote key for the first item, or null if not found.
//     */
//    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieDbData>): MovieRemoteKeyDbData? {
//        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
//            ?.let { movie -> local.getRemoteKey(movie.id) }
//    }
//
//    /**
//     * Gets the remote key for the last item in the current state.
//     *
//     * @param state The current paging state.
//     * @return The remote key for the last item, or null if not found.
//     */
//    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieDbData>): MovieRemoteKeyDbData? {
//        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
//            ?.let { movie -> local.getRemoteKey(movie.id) }
//    }
//}
