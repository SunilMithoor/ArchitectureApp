package com.sunil.app.presentation.ui.screens.movies.feed

data class FeedUiState(
    val showLoading: Boolean = true,
    val errorMessage: String? = null,
)

sealed class FeedNavigationState {
    data class MovieDetails(val movieId: Int = -1) : FeedNavigationState()
}
