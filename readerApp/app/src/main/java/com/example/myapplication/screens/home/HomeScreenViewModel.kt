package com.example.myapplication.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.DataOrException
import com.example.myapplication.model.Book
import com.example.myapplication.repository.BookRepository
import com.example.myapplication.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: FireRepository) :
    ViewModel() {
    val data: MutableState<DataOrException<List<Book>, Boolean, Exception>> = mutableStateOf(
        DataOrException(listOf(), true, Exception(""))
    )

    init {
        getAllBooksFromDb()
    }

    private fun getAllBooksFromDb() {
        viewModelScope.launch {
            data.value.loading = true

            data.value = repository.getAllBooksFromDb()

            if (!data.value.data.isNullOrEmpty()) {
                data
                    .value.loading = false
            }
        }
    }
}