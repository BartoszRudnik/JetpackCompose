package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.screens.ReaderSplashScreen
import com.example.myapplication.screens.details.BookDetailsScreen
import com.example.myapplication.screens.home.HomeScreenViewModel
import com.example.myapplication.screens.home.ReaderHomeScreen
import com.example.myapplication.screens.login.ReaderLoginScreen
import com.example.myapplication.screens.search.BookSearchViewModel
import com.example.myapplication.screens.search.ReaderBookSearchScreen
import com.example.myapplication.screens.stats.ReaderStatsScreen
import com.example.myapplication.screens.update.ReaderBookUpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {
        composable(ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController = navController)
        }
        composable(ReaderScreens.HomeScreen.name) {
            val viewModel = hiltViewModel<HomeScreenViewModel>()

            ReaderHomeScreen(navController = navController, viewModel)
        }
        composable(ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController = navController)
        }
        composable(ReaderScreens.StatsScreen.name) {
            val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()

            ReaderStatsScreen(navController = navController, viewModel = homeScreenViewModel)
        }
        composable(ReaderScreens.BookSearchScreen.name) {
            val viewModel = hiltViewModel<BookSearchViewModel>()

            ReaderBookSearchScreen(navController = navController, viewModel = viewModel)
        }

        val detailName = ReaderScreens.DetailsScreen.name
        composable("$detailName/{bookId}", arguments = listOf(navArgument("bookId") {
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let {
                BookDetailsScreen(navController = navController, bookId = it!!)
            }

        }

        composable(
            ReaderScreens.BookUpdateScreen.name + "/bookItemId", arguments = listOf(
                navArgument("bookItemId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("bookItemId").let {
                ReaderBookUpdateScreen(navController = navController, it!!)
            }
        }
    }
}