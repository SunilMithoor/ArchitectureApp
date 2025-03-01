package com.sunil.app.domain.usecase.movies

import androidx.paging.PagingData
import androidx.paging.insertSeparators
import com.sunil.app.presentation.entity.movies.MovieListItem
import javax.inject.Inject


/**
 * Use case to insert separators into a PagingData stream of MovieListItem.Movie.
 *
 * This class is responsible for adding separators (headers, footers, or category separators)
 * to a list of movies based on specific criteria.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
class InsertSeparatorIntoPagingData @Inject constructor() {

    /**
     * Inserts separators into the given PagingData of MovieListItem.Movie.
     *
     * @param moviePagingData The PagingData of MovieListItem.Movie to insert separators into.
     * @return A new PagingData with separators inserted.
     */fun insert(moviePagingData: PagingData<MovieListItem.Movie>): PagingData<MovieListItem> {
        return moviePagingData.insertSeparators { before: MovieListItem.Movie?, after: MovieListItem.Movie? ->
            when {
                isListEmpty(before, after) -> null
                isFirstItem(before) -> createHeaderItem(after)
                isLastItem(after) -> createFooterItem()
                hasDifferentCategory(before, after) -> createCategorySeparator(after)
                else -> null
            }
        }
    }


    /**
     * Checks if the list is empty (both before and after are null).
     *
     * @param before The item before the potential separator.
     * @param after The item after the potential separator.
     * @return True if the list is empty, false otherwise.
     */
    private fun isListEmpty(before: MovieListItem.Movie?, after: MovieListItem.Movie?): Boolean =
        before == null && after == null

    /**
     * Checks if the current item is the first item in the list (before is null).
     ** @param before The item before the potential separator.
     * @return True if it's the first item, false otherwise.
     */
    private fun isFirstItem(before: MovieListItem.Movie?): Boolean = before == null

    /**
     * Checks if the current item is the last item in the list (after is null).
     *
     * @param after The item after the potential separator.
     * @return True if it's the last item, false otherwise.
     */
    private fun isLastItem(after: MovieListItem.Movie?): Boolean = after == null

    /**
     * Checks if the before and after items belong to different categories.
     *
     * @param before The item before the potential separator.
     * @param after The item after the potential separator.
     * @return True if the categories are different, false otherwise.
     */
    private fun hasDifferentCategory(before: MovieListItem.Movie?, after: MovieListItem.Movie?): Boolean =
        before?.category != after?.category

    /**
     * Creates a header item for the list.
     *
     * @param after The item after the potential header.
     * @return A MovieListItem.Separator representing the header, or null if no header is needed.
     */
    private fun createHeaderItem(after: MovieListItem.Movie?): MovieListItem.Separator? =
        after?.let { MovieListItem.Separator("Header: ${it.category}") } // Example: Add "Header:" prefix

    /**
     * Creates a footer item for the list.
     *
     * In this example, we are not adding a footer.
     *
     * @return A MovieListItem.Separator representing the footer, or null if no footer is needed.
     */
    private fun createFooterItem(): MovieListItem.Separator? = null

    /**
     * Creates a category separator item.
     *
     * @param after The item after the potential separator.
     * @return A MovieListItem.Separator representing the category separator, or null if no separator is needed.
     */
    private fun createCategorySeparator(after: MovieListItem.Movie?): MovieListItem.Separator? =
        after?.let { MovieListItem.Separator("Category: ${it.category}") }
}
