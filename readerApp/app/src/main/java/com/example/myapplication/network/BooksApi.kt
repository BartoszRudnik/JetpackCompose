package com.example.myapplication.network

import com.example.myapplication.model.Book
import com.example.myapplication.model.Item
import com.example.myapplication.model.JsonBook
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksApi {
    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String): JsonBook

    @GET("volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookId: String): Item
}