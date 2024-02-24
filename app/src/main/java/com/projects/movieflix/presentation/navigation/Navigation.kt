package com.projects.movieflix.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.projects.movieflix.presentation.screen.moviedetails.MovieDetailsScreen
import com.projects.movieflix.presentation.screen.allmovies.AllMoviesScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.AllMoviesScreen.route
    ) {
        composable(route = Screen.AllMoviesScreen.route) {
            AllMoviesScreen(navController)
        }
        composable(
            route = Screen.MovieDetailsScreen.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            MovieDetailsScreen(navController)
        }
    }
}