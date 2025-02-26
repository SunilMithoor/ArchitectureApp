package com.sunil.app.presentation.ui.screens.main

import androidx.navigation.NavHostController
import com.sunil.app.presentation.navigation.Page

class MainRouter(
    private val mainNavController: NavHostController
) {

    fun navigateToSearch() {
        mainNavController.navigate(Page.Search)
    }

    fun navigateToMovieDetails(movieId: Int) {
        mainNavController.navigate(Page.MovieDetails(movieId))
    }
}
