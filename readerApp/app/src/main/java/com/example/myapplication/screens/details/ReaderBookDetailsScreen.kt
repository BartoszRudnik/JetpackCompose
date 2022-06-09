package com.example.myapplication.screens.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.myapplication.components.HomeScreenTopBar
import com.example.myapplication.data.DataOrException
import com.example.myapplication.model.Book
import com.example.myapplication.model.Item
import com.example.myapplication.screens.home.RoundedButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun BookDetailsScreen(
    navController: NavController,
    bookId: String,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        HomeScreenTopBar(
            title = "Book Details",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            showProfile = false,
        ) {
            navController.popBackStack()
        }
    }) {
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val bookInfo = produceState<DataOrException<Item, Boolean, Exception>>(
                    initialValue = DataOrException(null, true, Exception("")),
                ) {
                    value = viewModel.getBookInfo(bookId)
                }.value

                if (bookInfo.data == null) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        LinearProgressIndicator()
                        Text(text = "Loading")
                    }
                } else {
                    ShowBookDetails(bookInfo, navController)
                }
            }
        }
    }
}

@Composable
fun ShowBookDetails(
    bookInfo: DataOrException<Item, Boolean, Exception>,
    navController: NavController
) {
    val bookData = bookInfo.data?.volumeInfo
    val googleBookId = bookInfo.data?.id

    Column {
        Card(modifier = Modifier.padding(16.dp), shape = CircleShape, elevation = 4.dp) {
            Image(
                painter = rememberImagePainter(data = bookData?.imageLinks?.thumbnail),
                contentDescription = "book image",
                modifier = Modifier
                    .width(90.dp)
                    .height(90.dp)
                    .padding(4.dp)
            )
        }
        Text(
            text = bookData!!.title,
            style = MaterialTheme.typography.h6,
            overflow = TextOverflow.Ellipsis,
            maxLines = 4
        )
        Text(text = "Authors: ${bookData.authors}")
        Text(text = "Page count: ${bookData.pageCount}")
        Text(
            text = "Categories: ${bookData.categories}",
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            text = "Published: ${bookData.publishedDate}",
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(modifier = Modifier.height(10.dp))

        val localDims = LocalContext.current.resources.displayMetrics
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .height(localDims.heightPixels.dp.times(0.09f)),
            shape = RectangleShape,
            border = BorderStroke(width = 1.dp, color = Color.DarkGray)
        ) {
            val cleanDescription =
                HtmlCompat.fromHtml(bookData.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    .toString()

            LazyColumn(modifier = Modifier.padding(4.dp)) {
                item {
                    Text(text = cleanDescription)
                }
            }
        }
        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundedButton(label = "Save") {
                val book = Book(
                    title = bookData.title,
                    description = bookData.description,
                    authors = bookData.authors.toString(),
                    categories = bookData.categories.toString(),
                    pageCount = bookData.pageCount.toString(),
                    notes = "",
                    photoUrl = bookData.imageLinks.thumbnail,
                    publishedDate = bookData.publishedDate,
                    rating = 0.0,
                    googleBookId = googleBookId,
                    userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
                )

                saveToFirebase(book, navController)
            }
            RoundedButton(label = "Cancel") {
                navController.popBackStack()
            }
        }
    }
}

fun saveToFirebase(book: Book, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")

    if (book.toString().isNotEmpty()) {
        dbCollection.add(book).addOnSuccessListener { documentRef ->
            val docId = documentRef.id

            dbCollection.document(docId).update(hashMapOf("id" to docId) as Map<String, Any>)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navController.popBackStack()
                    }
                }
        }
    }
}
