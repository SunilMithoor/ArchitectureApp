package com.sunil.app.presentation.ui.screens.movies.feed

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.sunil.app.presentation.ui.screens.movies.feed.FeedNavigationState.MovieDetails
import com.sunil.app.presentation.viewmodel.movies.NavigationBarSharedViewModel
import com.sunil.app.presentation.entity.movies.MovieListItem
import com.sunil.app.presentation.navigation.Page
import com.sunil.app.presentation.ui.screens.main.MainRouter
import com.sunil.app.presentation.ui.util.collectAsEffect
import com.sunil.app.presentation.ui.util.preview.PreviewContainer
import com.sunil.app.presentation.ui.widget.LoaderFullScreen
import com.sunil.app.presentation.ui.widget.MovieList
import com.sunil.app.presentation.ui.widget.PullToRefresh
import com.sunil.app.presentation.viewmodel.movies.MoviesViewModel
import kotlinx.coroutines.flow.flowOf

/**
 * @author Sunil
 * @version 1.0
 * @since 2025-02-16
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedPage(
    mainRouter: MainRouter,
    viewModel: MoviesViewModel,
    sharedViewModel: NavigationBarSharedViewModel,
) {
    val moviesPaging = viewModel.feedMovies.collectAsLazyPagingItems()
    val uiState by viewModel.uiFeedMovieState.collectAsState()
    val pullToRefreshState = rememberPullRefreshState(uiState.showLoading, { viewModel.onFeedMovieRefresh() })
    val lazyGridState = rememberLazyGridState()

    viewModel.navigationFeedMovieState.collectAsEffect { navigationState ->
        when (navigationState) {
            is MovieDetails -> mainRouter.navigateToMovieDetails(navigationState.movieId)
        }
    }
    viewModel.refreshFeedMovieListState.collectAsEffect {
        moviesPaging.refresh()
    }

    sharedViewModel.bottomItem.collectAsEffect {
        if (it.page == Page.Feed) {
            lazyGridState.animateScrollToItem(0)
        }
    }

    LaunchedEffect(key1 = moviesPaging.loadState) {
        viewModel.onFeedMovieLoadStateUpdate(moviesPaging.loadState)
    }

    PullToRefresh(state = pullToRefreshState, refresh = uiState.showLoading) {
        FeedScreen(
            movies = moviesPaging,
            uiState = uiState,
            lazyGridState = lazyGridState,
            onMovieClick = viewModel::onFeedMovieClicked
        )
    }
}

@Composable
private fun FeedScreen(
    movies: LazyPagingItems<MovieListItem>,
    uiState: FeedUiState,
    lazyGridState: LazyGridState,
    onMovieClick: (movieId: Int) -> Unit
) {
    Surface {
        if (uiState.showLoading) {
            LoaderFullScreen()
        } else {
            MovieList(movies, onMovieClick, lazyGridState)
        }
    }
}

@Preview(device = Devices.PIXEL_3, showSystemUi = true)
@Composable
private fun FeedScreenPreview() {
    val movies = flowOf(
        PagingData.from(
            listOf<MovieListItem>(
                MovieListItem.Movie(9, "", ""),
                MovieListItem.Movie(9, "", ""),
                MovieListItem.Movie(9, "", ""),
                MovieListItem.Movie(9, "", ""),
                MovieListItem.Movie(9, "", ""),
                MovieListItem.Movie(9, "", ""),
                MovieListItem.Movie(9, "", ""),
                MovieListItem.Movie(9, "", ""),
            )
        )
    ).collectAsLazyPagingItems()
    PreviewContainer {
        FeedScreen(
            movies = movies,
            uiState = FeedUiState(
                showLoading = false,
                errorMessage = null,
            ),
            lazyGridState = rememberLazyGridState(),
            onMovieClick = {}
        )
    }
}
