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
