package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieapp.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MainContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MovieAppTheme {
        Scaffold(topBar = {
            TopAppBar(backgroundColor = Color.Magenta, elevation = 4.dp) {
                Text(text = "Movies")
            }
        }) {
            content()
        }
    }
}

@Composable
fun MainContent(
    movieList: List<String> = listOf(
        "Avatar",
        "300",
        "Harry Potter"
    )
) {
    Surface(color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.padding(12.dp)) {
            LazyColumn {
                items(items = movieList) {
                    MovieRow(movie = it) { movie ->

                    }
                }
            }
        }
    }
}

@Composable
fun MovieRow(movie: String, onItemClick: (String) -> Unit = {}) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(130.dp)
            .clickable {
                onItemClick(movie)
            },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 8.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp),
                shape = RectangleShape,
                elevation = 4.dp
            ) {
                Icon(imageVector = Icons.Default.AccountBox, "")
            }
            Text(text = movie)
        }
    }
}


