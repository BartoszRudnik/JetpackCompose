package com.example.myapplication.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.myapplication.components.HomeScreenTopBar
import com.example.myapplication.components.InputField
import com.example.myapplication.model.Book

@Composable
fun ReaderBookSearchScreen(navController: NavController) {
    Scaffold(topBar = {
        HomeScreenTopBar(
            navController = navController,
            title = "Search Books",
            icon = Icons.Default.ArrowBack
        ) {
            navController.popBackStack()
        }
    }) {
        Surface() {
            Column {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) { value ->

                }

                Spacer(modifier = Modifier.height(20.dp))
                BookList(navController)
            }
        }
    }
}

@Composable
fun BookList(navController: NavController) {
    val listOfBooks = emptyList<Book>()

    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        items(items = listOfBooks) { book ->
            BookRow(book, navController)
        }
    }
}

@Composable
fun BookRow(book: Book, navController: NavController) {
    Card(modifier = Modifier
        .clickable { }
        .fillMaxWidth()
        .height(100.dp)
        .padding(4.dp), shape = RectangleShape, elevation = 6.dp) {
        Row(
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val imageUrl = ""

            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = "book image",
                modifier = Modifier
                    .fillMaxHeight()
                    .width(80.dp)
                    .padding(4.dp)
            )

            Column() {
                Text(text = book.title.toString(), overflow = TextOverflow.Ellipsis)
                Text(
                    text = "Authors: " + book.authors.toString(),
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}
) {
    Column() {
        val searchQueryState = rememberSaveable {
            mutableStateOf("")
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value) {
            searchQueryState.value.trim().isNotEmpty()
        }

        InputField(
            valueState = searchQueryState,
            labelId = "Search",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions

                onSearch(searchQueryState.value.trim())

                searchQueryState.value = ""
                keyboardController?.hide()
            })
    }
}