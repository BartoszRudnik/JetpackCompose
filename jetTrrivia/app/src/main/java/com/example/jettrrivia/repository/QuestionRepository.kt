package com.example.jettrrivia.repository

import com.example.jettrrivia.data.DataOrException
import com.example.jettrrivia.model.Question
import com.example.jettrrivia.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionApi) {
    private val dataOrException = DataOrException<ArrayList<Question>, Boolean, Exception>()

    suspend fun getAllQuestions(): DataOrException<ArrayList<Question>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestion()

            if (dataOrException.data.toString().isNotEmpty()) {
                dataOrException.loading = false
            }
        } catch (exception: Exception) {
            dataOrException.e = exception
        }

        return dataOrException
    }
}