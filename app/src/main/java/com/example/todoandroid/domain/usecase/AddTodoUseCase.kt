package com.example.todoandroid.domain.usecase

import com.example.todoandroid.data.TodoRepository
import javax.inject.Inject

class AddTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(title: String, category: String? = null, isImportant: Boolean = false) {
        repository.add(title, category, isImportant)
    }
}

