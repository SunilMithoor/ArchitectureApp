package com.sunil.app.data.remote.retrofit.datasource.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sunil.app.data.local.db.entity.movies.MovieData
import com.sunil.app.domain.model.Result


//class SearchMoviePagingSource(
//    private val query: String,
//    private val remote: MovieDataSource.Remote
//) : PagingSource<Int, MovieData>() {
//
//    companion object{
//        private const val STARTING_PAGE_INDEX = 1
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> {
//        val page = params.key ?: STARTING_PAGE_INDEX
//
//        return when (val result = remote.searchMovies(query, page, params.loadSize)) {
//            is Result.Success -> LoadResult.Page(
//                data = result.data.distinctBy { movie -> movie.id },
//                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
//                nextKey = if (result.data.isEmpty()) null else page + 1
//            )
//
//            is Result.Error -> LoadResult.Error(result.error)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey
//        }
//    }
//}


/**
 * PagingSource for searching movies.
 *
 * This class handles the loading of movie data in pages based on a search query.
 * It interacts with a remote data source to fetch the movie data.
 *
 * @property query The search query string.* @property remote The remote data source for fetching movie data.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
class SearchMoviePagingSource(
    private val query: String,
    private val remote: MovieDataSource.Remote
) : PagingSource<Int, MovieData>() {

    companion object {
        /**
         * The starting page index for pagination.
         *
         * This constant defines the initial page number to be loaded when no specific page is requested.
         */
        private const val STARTING_PAGE_INDEX = 1
    }

    /**
     * Loads a page of movie databased on the given [LoadParams].
     *
     * This function is called by the Paging library to fetch a page of data.
     * It uses the [remote] data source to perform the search and returns a [LoadResult]
     * indicating the success or failure of the load operation.
     *
     * @param params The parameters for loading a page of data, including the requested page key and load size.
     * @return A [LoadResult] indicating the success or failure of the load operation.
     *         - [LoadResult.Page]: If the data was loaded successfully.
     *         - [LoadResult.Error]: If an error occurred during the load operation.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> {
        // Determine the page to load. If params.key is null, it's the first load, so use STARTING_PAGE_INDEX.
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            // Perform the search using the remote data source.
            val result = remote.searchMovies(query, page, params.loadSize)
            when (result) {
                is Result.Success -> {
                    // Remove duplicate movies based on their IDs.
                    val data = result.data.distinctBy { movie -> movie.id }
                    // Determine the next and previous page keys.
                    val nextKey = if (data.isEmpty()) null else page + 1
                    val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                    // Return a successful LoadResult.Page.
                    LoadResult.Page(
                        data = data,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                is Result.Error -> {
                    // Return an error LoadResult.Error.
                    LoadResult.Error(result.error)
                }
            }
        } catch (e: Exception) {
            // Return an error LoadResult.Error if any exception occurs.
            LoadResult.Error(e)
        }
    }

    /**
     * Returns the key for the refresh operation based on the current [PagingState].
     *
     * This function is called when the Paging library needs to refresh the data.
     * It determines the appropriate page key to use for refreshing the data,
     * based on the current state of the loaded data.
     *
     * @param state The current paging state.
     * @return The refresh key, or null if a refresh is not possible.
     */
    override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? {
        // Try to get the previous key of the closest page to the anchor position.
        // If not possible, try to get the next key and subtract 1.
        return state.anchorPosition?.let { anchorPosition ->
            val closestPage = state.closestPageToPosition(anchorPosition)
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }
    }
}
