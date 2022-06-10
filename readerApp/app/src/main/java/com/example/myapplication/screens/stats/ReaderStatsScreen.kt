package com.example.myapplication.screens.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.sharp.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.components.HomeScreenTopBar
import com.example.myapplication.model.Book
import com.example.myapplication.screens.home.HomeScreenViewModel
import com.example.myapplication.screens.search.BookRow
import com.example.myapplication.screens.search.BookRowV2
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ReaderStatsScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    var books: List<Book> = emptyList()
    val currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold(topBar = {
        HomeScreenTopBar(
            navController = navController,
            title = "Statistics",
            showProfile = false,
            icon = Icons.Default.ArrowBack,
        ) {
            navController.popBackStack()
        }
    }) {
        if (currentUser != null) {
            books = viewModel.data.value.data?.filter { book ->
                book.userId == currentUser.uid
            } ?: emptyList()
        }

        Column {
            Row {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(8.dp)
                ) {
                    Icon(imageVector = Icons.Sharp.Person, contentDescription = "person icon")
                }
                Text(text = "Hi ${currentUser?.email.toString().split("@")[0].uppercase()}")
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp), shape = CircleShape, elevation = 4.dp
            ) {
                val readBooksList: List<Book> = if (!viewModel.data.value.data.isNullOrEmpty()) {
                    books.filter { book ->
                        book.userId == currentUser?.uid && book.finishedReading != null
                    }
                } else {
                    emptyList()
                }

                val readingBooks = books.filter { book ->
                    book.finishedReading == null && book.startedReading != null
                }

                Column(modifier = Modifier.padding(4.dp), horizontalAlignment = Alignment.Start) {
                    Text(text = "Your stats", style = MaterialTheme.typography.h5)
                    Divider()
                    Text(text = "You're reading: ${readingBooks.size} books")
                    Divider()
                    Text(text = "You've read: ${readBooksList.size} books")
                }
            }
            if (viewModel.data.value.loading == true) {
                LinearProgressIndicator()
            } else {
                Divider()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    val readBooks: List<Book> = if (!viewModel.data.value.data.isNullOrEmpty()) {
                        viewModel.data.value.data!!.filter { book ->
                            book.userId == currentUser?.uid && book.finishedReading != null
                        }
                    } else {
                        emptyList()
                    }

                    items(items = readBooks) { book ->
                        BookRowV2(book = book, navController = navController)
                    }
                }
            }
        }

    }
}