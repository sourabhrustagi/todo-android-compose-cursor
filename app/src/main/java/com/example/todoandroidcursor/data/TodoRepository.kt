package com.example.todoandroidcursor.data

import com.example.todoandroidcursor.model.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

interface TodoRepository {
    val todos: StateFlow<List<Todo>>
    fun add(title: String)
    fun toggle(id: Long)
    fun delete(id: Long)
}

@Singleton
class InMemoryTodoRepository @Inject constructor() : TodoRepository {
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    override val todos: StateFlow<List<Todo>> = _todos.asStateFlow()

    override fun add(title: String) {
        if (title.isBlank()) return
        val newItem = Todo(
            id = System.currentTimeMillis(),
            title = title.trim(),
            completed = false
        )
        _todos.value = listOf(newItem) + _todos.value
    }

    override fun toggle(id: Long) {
        _todos.value = _todos.value.map { item ->
            if (item.id == id) item.copy(completed = !item.completed) else item
        }
    }

    override fun delete(id: Long) {
        _todos.value = _todos.value.filterNot { it.id == id }
    }
}



