package com.sunil.app.presentation.ui.screens.movies.favourites

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.sunil.app.R
import com.sunil.app.presentation.entity.movies.MovieListItem
import com.sunil.app.presentation.ui.screens.main.MainRouter
import com.sunil.app.presentation.ui.util.collectAsEffect
import com.sunil.app.presentation.ui.util.preview.PreviewContainer
import com.sunil.app.presentation.ui.widget.EmptyStateIcon
import com.sunil.app.presentation.ui.widget.EmptyStateView
import com.sunil.app.presentation.ui.widget.LoaderFullScreen
import com.sunil.app.presentation.ui.widget.MovieList
import com.sunil.app.presentation.viewmodel.movies.MoviesViewModel

import kotlinx.coroutines.flow.flowOf

@Composable
fun FavoritesPage(
    mainRouter: MainRouter,
    viewModel: MoviesViewModel,
) {
    val uiState by viewModel.uiFavouriteMovieState.collectAsState()
    val movies = viewModel.favouriteMovies.collectAsLazyPagingItems()
    viewModel.onFavouriteMovieLoadStateUpdate(movies.loadState, movies.itemCount)

    viewModel.navigationFavouriteMovieState.collectAsEffect { navigationState ->
        when (navigationState) {
            is FavoritesNavigationState.MovieDetails -> mainRouter.navigateToMovieDetails(navigationState.movieId)
        }
    }

    FavoritesScreen(
        favoriteUiState = uiState,
        movies = movies,
        onMovieClick = viewModel::onFavouriteMovieClicked
    )
}

@Composable
fun FavoritesScreen(
    favoriteUiState: FavoriteUiState,
    movies: LazyPagingItems<MovieListItem>,
    onMovieClick: (movieId: Int) -> Unit
) {
    Surface {
        val isLoading = favoriteUiState.isLoading
        val noDataAvailable = favoriteUiState.noDataAvailable

        if (isLoading) {
            LoaderFullScreen()
        } else {
            if (noDataAvailable) {
                EmptyStateView(
                    modifier = Modifier.padding(16.dp),
                    icon = EmptyStateIcon(iconRes = R.drawable.bg_empty_favorite),
                    title = stringResource(id = R.string.no_favorite_movies_title),
                    subtitle = stringResource(id = R.string.no_favorite_movies_subtitle)
                )
            } else {
                MovieList(movies = movies, onMovieClick = onMovieClick)
            }
        }
    }
}

@Preview(showSystemUi = true, name = "Light")
@Preview(showSystemUi = true, name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FavoritesScreenPreview() {
    val movieItems: List<MovieListItem> = listOf(
        MovieListItem.Separator("Action"),
        MovieListItem.Movie(1, "image1.jpg", "Action"),
        MovieListItem.Movie(2, "image2.jpg", "Comedy"),
        MovieListItem.Separator("Drama"),
        MovieListItem.Movie(3, "image3.jpg", "Drama")
    )

    PreviewContainer {
        val movies = flowOf(PagingData.from(movieItems)).collectAsLazyPagingItems()
        FavoritesScreen(FavoriteUiState(isLoading = false, noDataAvailable = false), movies) {}
    }
}

@Preview(showSystemUi = true, name = "Light")
@Preview(showSystemUi = true, name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FavoritesScreenNoDataPreview() {
    val movieItems: List<MovieListItem> = listOf(MovieListItem.Movie(1, "image1.jpg", "Action"))

    PreviewContainer {
        val movies = flowOf(PagingData.from(movieItems)).collectAsLazyPagingItems()
        FavoritesScreen(FavoriteUiState(isLoading = false, noDataAvailable = true), movies) {}
    }
}
