package com.sunil.app.presentation.ui.screens.movies.navigationbar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sunil.app.presentation.navigation.route
import com.sunil.app.presentation.ui.screens.main.MainRouter
import com.sunil.app.presentation.ui.theme.AppColor.GrayB3
import com.sunil.app.presentation.ui.util.preview.PreviewContainer
import com.sunil.app.presentation.ui.widget.BottomNavigationBar
import com.sunil.app.presentation.ui.widget.TopBar

import com.sunil.app.presentation.viewmodel.movies.NavigationBarSharedViewModel

@Composable
fun NavigationBarScreen(
    sharedViewModel: NavigationBarSharedViewModel,
    mainRouter: MainRouter,
    darkMode: Boolean,
    onThemeUpdated: () -> Unit,
    nestedNavController: NavHostController,
    content: @Composable () -> Unit
) {
    val uiState = NavigationBarUiState()
    Scaffold(
        topBar = {
            TopBar(
                "MovieClean",
                darkMode,
                onThemeUpdated = onThemeUpdated,
                onSearchClick = {
                    mainRouter.navigateToSearch()
                }
            )

        },
        bottomBar = {
            BottomNavigationBar(
                items = uiState.bottomItems,
                navController = nestedNavController,
                onItemClick = { bottomItem ->
                    val currentPageRoute = nestedNavController.currentDestination?.route
                    val clickedPageRoute = bottomItem.page
                    val notSamePage = currentPageRoute != clickedPageRoute.route()
                    if (notSamePage) {
                        nestedNavController.navigate(clickedPageRoute) {
                            launchSingleTop = true
                            popUpTo(nestedNavController.graph.findStartDestination().id)
                        }
                    }
                    sharedViewModel.onBottomItemClicked(bottomItem)
                }
            )
        }
    ) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize(1f)
                .padding(paddingValues)
        ) {
            content()
        }
    }
}

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NavigationBarScreenPreview() = PreviewContainer {
    val navController = rememberNavController()
    val mainRouter = MainRouter(navController)
    val darkTheme = isSystemInDarkTheme()

    NavigationBarScreen(
        sharedViewModel = NavigationBarSharedViewModel(),
        mainRouter = mainRouter,
        darkMode = darkTheme,
        onThemeUpdated = { },
        nestedNavController = navController,
        content = {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(GrayB3)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 20.sp,
                    text = "Page Content"
                )
            }
        }
    )
}
