package com.example.todoandroid.domain.usecase

import com.example.todoandroid.data.TodoRepository
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.delete(id)
    }
}

