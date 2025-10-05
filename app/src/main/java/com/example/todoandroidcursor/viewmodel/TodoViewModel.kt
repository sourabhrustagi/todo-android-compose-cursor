package com.example.todoandroidcursor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoandroidcursor.domain.usecase.AddTodoUseCase
import com.example.todoandroidcursor.domain.usecase.DeleteTodoUseCase
import com.example.todoandroidcursor.domain.usecase.EditTodoUseCase
import com.example.todoandroidcursor.domain.usecase.GetTodosUseCase
import com.example.todoandroidcursor.domain.usecase.ToggleTodoUseCase
import com.example.todoandroidcursor.model.Todo
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
    private val deleteTodoUseCase: DeleteTodoUseCase
) : ViewModel() {
    val todos: StateFlow<List<Todo>> = getTodosUseCase()

    fun addTodo(title: String, category: String? = null) {
        viewModelScope.launch {
            addTodoUseCase(title, category)
        }
    }

    fun editTodo(id: Long, newTitle: String, category: String? = null) {
        viewModelScope.launch {
            editTodoUseCase(id, newTitle, category)
        }
    }

    fun toggleTodo(id: Long) {
        viewModelScope.launch {
            toggleTodoUseCase(id)
        }
    }

    fun deleteTodo(id: Long) {
        viewModelScope.launch {
            deleteTodoUseCase(id)
        }
    }
}



