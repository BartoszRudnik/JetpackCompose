package com.example.myapplication.navigation

import android.window.SplashScreen
import com.example.myapplication.screens.ReaderSplashScreen
import java.lang.IllegalArgumentException

enum class ReaderScreens {
    DetailsScreen,
    HomeScreen,
    LoginScreen,
    BookSearchScreen,
    StatsScreen,
    BookUpdateScreen,
    SplashScreen;

    companion object {
        fun fromRoute(route: String?): ReaderScreens = when (route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            HomeScreen.name -> HomeScreen
            BookUpdateScreen.name -> BookUpdateScreen
            DetailsScreen.name -> DetailsScreen
            StatsScreen.name -> StatsScreen
            BookSearchScreen.name -> BookSearchScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}