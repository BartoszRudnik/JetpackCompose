package com.example.myapplication.screens.details

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.DataOrException
import com.example.myapplication.model.Item
import com.example.myapplication.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {
    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {
        return repository.getBookInfo(bookId)
    }
}