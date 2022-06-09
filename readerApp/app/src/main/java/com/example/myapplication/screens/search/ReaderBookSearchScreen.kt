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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.myapplication.components.HomeScreenTopBar
import com.example.myapplication.components.InputField
import com.example.myapplication.model.Item
import com.example.myapplication.navigation.ReaderScreens

@Composable
fun ReaderBookSearchScreen(
    navController: NavController,
    viewModel: BookSearchViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        HomeScreenTopBar(
            navController = navController,
            title = "Search Books",
            icon = Icons.Default.ArrowBack
        ) {
            navController.popBackStack()
        }
    }) {
        Surface {
            Column {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    viewModel = viewModel,
                ) { value ->
                    viewModel.searchBooks(value)
                }

                Spacer(modifier = Modifier.height(20.dp))
                BookList(navController, viewModel)
            }
        }
    }
}

@Composable
fun BookList(navController: NavController, viewModel: BookSearchViewModel) {
    if (viewModel.listOfBooks.value.loading!!) {
        CircularProgressIndicator()
    }

    val listOfBooks = viewModel.listOfBooks.value.data

    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        items(items = listOfBooks!!) { item ->
            BookRow(item, navController)
        }
    }
}

@Composable
fun BookRow(book: Item, navController: NavController) {
    Card(modifier = Modifier
        .clickable {
            navController.navigate(ReaderScreens.DetailsScreen.name + "/${book.id}")
        }
        .fillMaxWidth()
        .height(100.dp)
        .padding(4.dp), shape = RectangleShape, elevation = 6.dp) {
        Row(
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val imageUrl = book.volumeInfo.imageLinks.smallThumbnail

            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = "book image",
                modifier = Modifier
                    .fillMaxHeight()
                    .width(80.dp)
                    .padding(4.dp)
            )

            Column {
                Text(text = book.volumeInfo.title, overflow = TextOverflow.Ellipsis)
                Text(
                    text = "Authors: " + book.volumeInfo.authors.toString(),
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic,
                )
                Text(
                    text = "Date: " + book.volumeInfo.publishedDate,
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic,
                )
                Text(
                    text = "Publisher: " + book.volumeInfo.publisher,
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic,
                )
                Text(
                    text = book.volumeInfo.categories.toString(),
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic,
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
    viewModel: BookSearchViewModel,
    onSearch: (String) -> Unit = {}
) {
    Column {
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