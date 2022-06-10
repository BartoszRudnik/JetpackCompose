package com.example.myapplication.screens.update

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.myapplication.components.HomeScreenTopBar
import com.example.myapplication.components.InputField
import com.example.myapplication.data.DataOrException
import com.example.myapplication.model.Book
import com.example.myapplication.screens.home.HomeScreenViewModel
import com.example.myapplication.screens.home.RoundedButton
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp
import java.time.Instant.now

@Composable
fun ReaderBookUpdateScreen(
    navController: NavController,
    id: String,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        HomeScreenTopBar(
            navController = navController,
            title = "Update Book",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
        ) {
            navController.popBackStack()
        }
    }) {
        val bookInfo = produceState<DataOrException<List<Book>, Boolean, Exception>>(
            initialValue = DataOrException(
                data = emptyList(),
                loading = true,
                e = Exception("")
            )
        ) {
            value = viewModel.data.value
        }.value

        androidx.compose.material.Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(2.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (bookInfo.loading!!) {
                    LinearProgressIndicator()
                } else {
                    androidx.compose.material.Surface(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        shape = CircleShape,
                        elevation = 4.dp
                    ) {
                        ShowBookUpdate(bookInfo = viewModel.data.value, bookItemId = id)
                        ShowSimpleForm(book = viewModel.data.value.data!!.first { book ->
                            book.googleBookId == id
                        }, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun ShowSimpleForm(book: Book, navController: NavController) {
    val notesText = remember {
        mutableStateOf("")
    }
    val startedReading = remember {
        mutableStateOf(book.startedReading != null)
    }
    val finishedReading = remember {
        mutableStateOf(book.finishedReading != null)
    }

    SimpleForm(
        defaultValue = book.notes.toString().ifEmpty { "No thoughts available" }
    ) { note ->
        notesText.value = note
    }
    Row(
        modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TextButton(
            onClick = { startedReading.value = true },
            enabled = book.startedReading == null
        ) {
            if (startedReading.value) {
                Text(
                    text = "Started Reading",
                    modifier = Modifier.alpha(0.5f),
                    color = Color.Red.copy(0.5f)
                )
            } else {
                Text(text = "Start Reading")
            }
        }

        TextButton(
            onClick = { finishedReading.value = true },
            enabled = book.finishedReading == null
        ) {
            if (book.finishedReading == null) {
                if (!finishedReading.value) {
                    Text(text = "Mark as Read")
                } else {
                    Text(text = "Finished Reading!")
                }
            } else {
                Text(text = "Finished on: ${book.finishedReading}")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            RoundedButton(label = "Update") {
                val changedNote = book.notes != notesText.value
                val isFinishedTimestamp =
                    if (finishedReading.value) com.google.firebase.Timestamp.now() else book.finishedReading
                val isStartedTimestamp =
                    if (startedReading.value) com.google.firebase.Timestamp.now() else book.startedReading

                val bookUpdate = changedNote || finishedReading.value || startedReading.value



                if (bookUpdate) {
                    val bookToUpdate = hashMapOf(
                        "finished_reading_at" to isFinishedTimestamp,
                        "started_reading_at" to isStartedTimestamp,
                        "notes" to notesText.value
                    ).toMap()

                    FirebaseFirestore.getInstance().collection("books").document(book.id!!)
                        .update(bookToUpdate).addOnCompleteListener { task ->
                        }.addOnFailureListener {

                        }
                }
            }
            RoundedButton(label = "Delete")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    defaultValue: String = "Great Book",
    onSearch: (String) -> Unit = {}
) {
    Column() {
        val textFieldValue = rememberSaveable {
            mutableStateOf(defaultValue)
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(textFieldValue) {
            textFieldValue.value.trim().isNotEmpty()
        }

        InputField(
            modifier = modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(4.dp)
                .background(Color.White, CircleShape),
            valueState = textFieldValue,
            labelId = "Enter your thoughts",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions

                onSearch(textFieldValue.value.trim())
                keyboardController!!.hide()
            })
    }
}

@Composable
fun ShowBookUpdate(bookInfo: DataOrException<List<Book>, Boolean, Exception>, bookItemId: String) {
    Row() {
        Spacer(modifier = Modifier.width(40.dp))

        if (bookInfo.data != null) {
            Column(modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.Center) {
                CardListItem(book = bookInfo.data!!.first { mBook ->
                    mBook.googleBookId == bookItemId
                }, onPressDetails = {})
            }
        }
    }
}

@Composable
fun CardListItem(book: Book, onPressDetails: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {

            }, elevation = 4.dp
    ) {
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            Image(
                painter = rememberImagePainter(data = book.photoUrl.toString()),
                contentDescription = "",
                modifier = Modifier
                    .height(100.dp)
                    .width(120.dp)
                    .padding(4.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 120.dp,
                            topEnd = 20.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        )
                    )
            )
            Column() {
                Text(
                    text = book.title.toString(),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(4.dp)
                        .width(120.dp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.authors.toString(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = book.publishedDate.toString(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}
