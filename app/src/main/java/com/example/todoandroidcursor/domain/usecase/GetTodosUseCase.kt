package com.example.todoandroidcursor.domain.usecase

import com.example.todoandroidcursor.data.TodoRepository
import kotlinx.coroutines.flow.StateFlow
import com.example.todoandroidcursor.model.Todo
import javax.inject.Inject

class GetTodosUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    operator fun invoke(): StateFlow<List<Todo>> {
        return repository.todos
    }
}

