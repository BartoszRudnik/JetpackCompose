package com.example.jettrrivia.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jettrrivia.screen.QuestionsViewModel

@Composable
fun Questions(viewModel: QuestionsViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()

    if (viewModel.data.value.loading == true) {
        CircularProgressIndicator()
    }
}

@Composable
fun QuestionDisplay() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp), color = Color.Magenta
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            QuestionTracker()
        }
    }
}

@Composable
fun QuestionTracker(counter: Int = 10, outOf: Int = 20) {
    Text(text = "Question $counter / $outOf", modifier = Modifier.padding(20.dp))

}