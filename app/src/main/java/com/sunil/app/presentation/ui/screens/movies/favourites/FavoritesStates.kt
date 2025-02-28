package com.sunil.app.presentation.ui.screens.movies.favourites

data class FavoriteUiState(
    val isLoading: Boolean = true,
    val noDataAvailable: Boolean = false
)

sealed class FavoritesNavigationState {
    data class MovieDetails(val movieId: Int) : FavoritesNavigationState()
}
