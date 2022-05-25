package com.example.jettrrivia.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jettrrivia.data.DataOrException
import com.example.jettrrivia.model.Question
import com.example.jettrrivia.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val questionRepository: QuestionRepository) :
    ViewModel() {
    val data: MutableState<DataOrException<ArrayList<Question>,
            Boolean, Exception>> = mutableStateOf(
        DataOrException(null, true, Exception("")) )

    init {
        getAllQuestions()
    }

    private fun getAllQuestions() {
        viewModelScope.launch {
            data.value.loading = true

            data.value = questionRepository.getAllQuestions()

            if (data.value.data.toString().isNotEmpty()) {
                data.value.loading = false
            }
        }
    }
}