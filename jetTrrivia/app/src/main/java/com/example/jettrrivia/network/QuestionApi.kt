package com.example.jettrrivia.network

import com.example.jettrrivia.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {
    @GET("world.json")
    suspend fun getAllQuestion(): ArrayList<Question>
}