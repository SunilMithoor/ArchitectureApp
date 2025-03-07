package com.sunil.app.presentation.navigation

import kotlinx.serialization.Serializable


sealed class Page {
    @Serializable
    data object NavigationBar : Page()

    @Serializable
    data object Feed : Page()

    @Serializable
    data object Favorites : Page()

    @Serializable
    data object Search : Page()

    @Serializable
//    data class MovieDetails(val movieId: Int) : Page()
    data class MovieDetails(val movieId: Int = -1) : Page()
}

sealed class Graph {
    @Serializable
    data object Main : Graph()
}

fun Page.route(): String? {
    return this.javaClass.canonicalName
}


///**
// * Represents the different screens or pages in the application.
// * Each data object represents a unique screen.
// */
//sealed class Page(val route: String) {
//    /**
//     * Represents the main navigation bar screen.
//     */
//
//    data object NavigationBar : Page("navigation_bar")
//
//    /**
//     * Represents the feed screen.
//     */
//
//    data object Feed : Page("feed")
//
//    /**
//     * Represents the favorites screen.
//     */
//
//    data object Favorites : Page("favorites")
//
//    /**
//     * Represents the search screen.
//     */
//
//    data object Search : Page("search")
//
//    /**
//     * Represents the movie details screen.
//     * @param movieId The ID of the movie to display details for.
//     */
//
//    data class MovieDetails(val movieId: Int) : Page("movie_details/{movieId}") {
//        companion object {
//            const val MOVIE_ID_ARG = "movieId"
//            fun createRoute(movieId: Int): String {
//                return "movie_details/$movieId"
//            }
//        }
//    }
//}
//
///**
// * Represents the different navigation graphs in the application.
// * Each data object represents a unique navigation graph.
// */
//sealed class Graph(val route: String) {
//    /**
//     * Represents the main navigation graph.
//     */
//    data object Main : Graph("main_graph")
//}
//
///**
// * Extension function to get the route of a Page.
// *
// * @return The route string associated with the Page.
// */
////fun Page.route(): String {
////    return when (this) {
////        Page.NavigationBar -> "navigation_bar"
////        Page.Feed -> "feed"
////        Page.Favorites -> "favorites"
////        Page.Search -> "search"
////        is Page.MovieDetails -> "movie_details/${this.movieId}"
////    }
////}
