package com.example.myapplication.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.components.HomeScreenTopBar
import com.example.myapplication.data.DataOrException
import com.example.myapplication.model.Item

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
                    LinearProgressIndicator()
                } else {
                    Text(text = "Book description:")
                }
            }
        }
    }
}