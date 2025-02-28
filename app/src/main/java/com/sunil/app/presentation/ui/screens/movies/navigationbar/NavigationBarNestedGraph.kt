package com.sunil.app.presentation.ui.screens.movies.navigationbar

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sunil.app.presentation.navigation.Page
import com.sunil.app.presentation.ui.screens.main.MainRouter
import com.sunil.app.presentation.ui.screens.movies.favourites.FavoritesPage
import com.sunil.app.presentation.ui.screens.movies.feed.FeedPage
import com.sunil.app.presentation.ui.util.composableHorizontalSlide
import com.sunil.app.presentation.ui.util.sharedViewModel
import com.sunil.app.presentation.viewmodel.movies.MoviesViewModel
import kotlin.reflect.KClass

@Composable
fun NavigationBarNestedGraph(
    navController: NavHostController,
    mainNavController: NavHostController,
    parentRoute: KClass<*>?
) {
    NavHost(
        navController = navController,
        startDestination = Page.Feed,
        route = parentRoute
    ) {
        composableHorizontalSlide<Page.Feed> { backStack ->
            val viewModel = hiltViewModel<MoviesViewModel>()
            FeedPage(
                mainRouter = MainRouter(mainNavController),
                viewModel = viewModel,
                sharedViewModel = backStack.sharedViewModel(navController = mainNavController)
            )
        }
        composableHorizontalSlide<Page.Favorites> {
            val viewModel = hiltViewModel<MoviesViewModel>()
            FavoritesPage(
                mainRouter = MainRouter(mainNavController),
                viewModel = viewModel,
            )
        }
    }
}
