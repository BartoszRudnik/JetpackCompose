package com.example.myapplication.repository

import com.example.myapplication.data.DataOrException
import com.example.myapplication.model.Book
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireRepository @Inject constructor(private val queryBook: Query) {
    suspend fun getAllBooksFromDb(): DataOrException<List<Book>, Boolean, Exception> {
        val dataOrException = DataOrException<List<Book>, Boolean, Exception>()

        try {
            dataOrException.loading = true

            dataOrException.data = queryBook.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(Book::class.java)!!
            }

            dataOrException.loading = false
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }

        return dataOrException
    }
}