package com.example.todoandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoandroid.domain.usecase.AddTodoUseCase
import com.example.todoandroid.domain.usecase.DeleteTodoUseCase
import com.example.todoandroid.domain.usecase.EditTodoUseCase
import com.example.todoandroid.domain.usecase.GetTodosUseCase
import com.example.todoandroid.domain.usecase.ToggleTodoUseCase
import com.example.todoandroid.domain.usecase.ToggleImportantUseCase
import com.example.todoandroid.model.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    private val editTodoUseCase: EditTodoUseCase,
    private val toggleTodoUseCase: ToggleTodoUseCase,
    private val toggleImportantUseCase: ToggleImportantUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) : ViewModel() {
    val todos: StateFlow<List<Todo>> = getTodosUseCase()

    fun addTodo(title: String, category: String? = null, isImportant: Boolean = false) {
        viewModelScope.launch {
            addTodoUseCase(title, category, isImportant)
        }
    }

    fun editTodo(id: Long, newTitle: String, category: String? = null, isImportant: Boolean? = null) {
        viewModelScope.launch {
            editTodoUseCase(id, newTitle, category, isImportant)
        }
    }

    fun toggleTodo(id: Long) {
        viewModelScope.launch {
            toggleTodoUseCase(id)
        }
    }

    fun toggleImportant(id: Long) {
        viewModelScope.launch {
            toggleImportantUseCase(id)
        }
    }

    fun deleteTodo(id: Long) {
        viewModelScope.launch {
            deleteTodoUseCase(id)
        }
    }
}



