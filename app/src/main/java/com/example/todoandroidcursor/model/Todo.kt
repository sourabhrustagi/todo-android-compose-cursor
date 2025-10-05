package com.example.todoandroidcursor.model

data class Todo(
    val id: Long,
    val title: String,
    val completed: Boolean,
    val category: String? = null
)



