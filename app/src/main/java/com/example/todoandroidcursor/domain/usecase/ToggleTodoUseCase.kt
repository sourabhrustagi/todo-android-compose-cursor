package com.example.todoandroidcursor.domain.usecase

import com.example.todoandroidcursor.data.TodoRepository
import javax.inject.Inject

class ToggleTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.toggle(id)
    }
}

