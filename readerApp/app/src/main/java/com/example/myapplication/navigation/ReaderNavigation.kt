package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.ReaderSplashScreen
import com.example.myapplication.screens.home.ReaderHomeScreen
import com.example.myapplication.screens.login.ReaderLoginScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {
        composable(ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController = navController)
        }
        composable(ReaderScreens.HomeScreen.name) {
            ReaderHomeScreen(navController = navController)
        }
        composable(ReaderScreens.HomeScreen.name) {
            ReaderLoginScreen(navController = navController)
        }
    }
}