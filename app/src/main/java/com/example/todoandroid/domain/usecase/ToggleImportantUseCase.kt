package com.example.todoandroid.domain.usecase

import com.example.todoandroid.data.TodoRepository
import javax.inject.Inject

class ToggleImportantUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.toggleImportant(id)
    }
}
