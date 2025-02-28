package com.sunil.app.data.remote.retrofit.datasource.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sunil.app.data.local.db.entity.movies.MovieData
import com.sunil.app.domain.model.Result

/**
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
class SearchMoviePagingSource(
    private val query: String,
    private val remote: MovieDataSource.Remote
) : PagingSource<Int, MovieData>() {

    companion object{
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return when (val result = remote.search(query, page, params.loadSize)) {
            is Result.Success -> LoadResult.Page(
                data = result.data.distinctBy { movie -> movie.id },
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (result.data.isEmpty()) null else page + 1
            )

            is Result.Error -> LoadResult.Error(result.error)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}
