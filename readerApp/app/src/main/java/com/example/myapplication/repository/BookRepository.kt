package com.example.myapplication.repository

import com.example.myapplication.data.DataOrException
import com.example.myapplication.model.Book
import com.example.myapplication.model.Item
import com.example.myapplication.network.BooksApi
import javax.inject.Inject
import kotlin.math.sin

class BookRepository @Inject constructor(private val api: BooksApi) {
    private val dataOrException = DataOrException<List<Item>, Boolean, Exception>()
    private val singleBookDataOrException = DataOrException<Item, Boolean, Exception>()


    suspend fun getBooks(searchQuery: String): DataOrException<List<Item>, Boolean, Exception> {
        try {
            dataOrException.loading = true

            dataOrException.data = api.getAllBooks(searchQuery).items

            dataOrException.loading = false
        } catch (e: Exception) {
            dataOrException.e = e
        }

        return dataOrException
    }

    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {
        try {
            singleBookDataOrException.loading = true

            singleBookDataOrException.data = api.getBookInfo(bookId)

            singleBookDataOrException.loading = false
        } catch (e: Exception) {
            singleBookDataOrException.e = e
        }

        return singleBookDataOrException
    }
}