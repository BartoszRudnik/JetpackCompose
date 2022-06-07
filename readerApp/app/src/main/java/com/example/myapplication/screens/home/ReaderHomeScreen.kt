package com.example.myapplication.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.model.Book
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
            HomeContent(navController = navController)
        }
    }
}

@Composable
fun HomeContent(navController: NavController) {
    val currentUserName = FirebaseAuth.getInstance().currentUser?.email!!.split("@")[0]

    Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.SpaceEvenly) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "Your reading \n" + " activity right now")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "account icon",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ReaderScreens.StatsScreen.name)
                        }
                        .size(50.dp), tint = MaterialTheme.colors.secondaryVariant
                )
                Text(
                    text = currentUserName,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.overline,
                    color = Color.Red,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                Divider()
            }
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
            Icon(
                imageVector = Icons.Filled.Logout,
                contentDescription = "logout icon",
                tint = Color.Green.copy(alpha = 0.6f)
            )
        }
    }, backgroundColor = Color.Transparent, elevation = 0.dp)
}

@Composable
fun TitleSection(modifier: Modifier = Modifier, label: String) {
    Surface(modifier = modifier.padding(5.dp)) {
        Column() {
            Text(
                text = label,
                fontSize = 18.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Left
            )
        }
    }
}

@Composable
fun ReadingRightNowArea(books: List<Book>, navController: NavController) {
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
