package com.example.todoandroid.domain.usecase

import com.example.todoandroid.data.TodoRepository
import kotlinx.coroutines.flow.StateFlow
import com.example.todoandroid.model.Todo
import javax.inject.Inject

class GetTodosUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    operator fun invoke(): StateFlow<List<Todo>> {
        return repository.todos
    }
}

