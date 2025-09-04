package com.example.todoandroidcursor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todoandroidcursor.data.InMemoryTodoRepository
import com.example.todoandroidcursor.data.TodoRepository
import com.example.todoandroidcursor.model.Todo
import kotlinx.coroutines.flow.StateFlow

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
    val todos: StateFlow<List<Todo>> = repository.todos

    fun addTodo(title: String) = repository.add(title)
    fun toggleTodo(id: Long) = repository.toggle(id)
    fun deleteTodo(id: Long) = repository.delete(id)

    companion object {
        fun factory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repo: TodoRepository = InMemoryTodoRepository()
                TodoViewModel(repo)
            }
        }
    }
}



