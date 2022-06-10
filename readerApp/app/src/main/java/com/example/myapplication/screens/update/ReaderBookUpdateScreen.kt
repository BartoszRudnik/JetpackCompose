package com.example.myapplication.screens.update

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.myapplication.components.HomeScreenTopBar
import com.example.myapplication.data.DataOrException
import com.example.myapplication.model.Book
import com.example.myapplication.screens.home.HomeScreenViewModel

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
                    }
                }
            }
        }
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
