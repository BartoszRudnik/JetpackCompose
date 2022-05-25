package com.example.jettrrivia.components

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import com.example.jettrrivia.screen.QuestionsViewModel

@Composable
fun Questions(viewModel: QuestionsViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()

    if (viewModel.data.value.loading == true) {
        CircularProgressIndicator()
    }
}