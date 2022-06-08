package com.example.myapplication.model

data class JsonBook(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)