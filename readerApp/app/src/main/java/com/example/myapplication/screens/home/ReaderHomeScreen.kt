package com.example.myapplication.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ReaderHomeScreen(navController: NavController) {
    Scaffold(topBar = {
        HomeScreenTopBar(navController = navController, title = "A. Reader")
    }, floatingActionButton = {
        FABContent { value ->
        }
    }) {
        Surface(modifier = Modifier.fillMaxSize()) {

        }
    }
}

@Composable
fun HomeScreenTopBar(navController: NavController, title: String, showProfile: Boolean = true) {
    TopAppBar(title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (showProfile) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "",
                    modifier = Modifier.padding(4.dp)
                )
            }
            Text(
                text = title,
                color = Color.Red.copy(alpha = 0.6f),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.width(150.dp))
        }
    }, actions = {
        IconButton(onClick = {
            FirebaseAuth.getInstance().signOut().run {
                navController.navigate(ReaderScreens.LoginScreen.name)
            }
        }) {
            Icons.Default.Logout
        }
    }, backgroundColor = Color.Transparent, elevation = 0.dp)
}

@Composable
fun FABContent(onTap: (String) -> Unit) {
    FloatingActionButton(
        onClick = {
            onTap("")
        },
        shape = RoundedCornerShape(50.dp),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "add icon",
            tint = MaterialTheme.colors.onSecondary
        )
    }
}
