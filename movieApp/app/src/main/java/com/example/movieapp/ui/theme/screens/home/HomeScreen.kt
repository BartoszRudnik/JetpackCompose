package com.example.movieapp.ui.theme.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieapp.model.Movie
import com.example.movieapp.model.getMovie
import com.example.movieapp.ui.theme.navigation.MovieScreens
import com.example.movieapp.widgets.MovieRow

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(backgroundColor = Color.Magenta, elevation = 4.dp) {
            Text(text = "Movies")
        }
    }) {
        MainContent(navController)
    }
}


@Composable
fun MainContent(
    navController: NavController,
    movieList: List<Movie> = getMovie()
) {
    Surface(color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.padding(12.dp)) {
            LazyColumn {
                items(items = movieList) {
                    MovieRow(movie = it) { movie ->
                        navController.navigate(MovieScreens.DetailsScreen.name + "/$movie")
                    }
                }
            }
        }
    }
}

