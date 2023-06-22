package com.dicoding.animelist

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.animelist.data.local.navigation.Screen
import com.dicoding.animelist.ui.component.BottomBar
import com.dicoding.animelist.ui.detail.Detail
import com.dicoding.animelist.ui.favorit.HighlightScreen
import com.dicoding.animelist.ui.home.HomeScreen

@Composable
fun AnimeListApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = modifier.padding(innerPadding)
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(
                        navigateToDetail = { id ->
                            navController.navigate(Screen.Detail.createRoute(id))
                        },
                    )
                }
                composable(
                    route = Screen.Detail.route,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) {
                    Detail(
                        id = it.arguments?.getInt("id") ?: 0,
                        navigateBack = {
                            navController.navigateUp()
                        },
                        navigateToHighlight = {
                            navController.popBackStack()
                            navController.navigate(Screen.Favorite.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
                composable(Screen.Favorite.route) {
                    HighlightScreen(
                        navigateToDetail = { id ->
                            navController.navigate(Screen.Detail.createRoute(id))
                        },
                    )
                }
            }
        }
    )
}