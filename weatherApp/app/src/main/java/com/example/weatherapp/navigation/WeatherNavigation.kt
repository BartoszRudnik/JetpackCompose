package com.example.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherapp.screen.MainViewModel
import com.example.weatherapp.screen.WeatherMainScreen
import com.example.weatherapp.screen.WeatherSearchScreen
import com.example.weatherapp.screen.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        val mainRoute = WeatherScreens.MainScreen.name
        composable(
            "$mainRoute/{city}",
            arguments = listOf(
                navArgument(name = "city") {
                    type = NavType.StringType
                }
            )
        ) { navBack ->

            navBack.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()

                WeatherMainScreen(navController = navController, mainViewModel, city = city!!)
            }
        }

        composable(WeatherScreens.SearchScreen.name) {
            WeatherSearchScreen(navController = navController)
        }
    }
}