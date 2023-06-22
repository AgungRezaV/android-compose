package com.dicoding.animelist.data.local.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Detail: Screen("home/{id}") {
        fun createRoute(id: Int) = "home/$id"
    }
    object Favorite: Screen("favorite")
    object Profile: Screen("profile")
}