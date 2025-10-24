package com.example.todoandroid.data

import com.example.todoandroid.model.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

interface TodoRepository {
    val todos: StateFlow<List<Todo>>
    fun add(title: String, category: String? = null, isImportant: Boolean = false)
    fun edit(id: Long, newTitle: String, category: String? = null, isImportant: Boolean? = null)
    fun toggle(id: Long)
    fun toggleImportant(id: Long)
    fun delete(id: Long)
}

@Singleton
class InMemoryTodoRepository @Inject constructor() : TodoRepository {
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    override val todos: StateFlow<List<Todo>> = _todos.asStateFlow()

    override fun add(title: String, category: String?, isImportant: Boolean) {
        if (title.isBlank()) return
        val newItem = Todo(
            id = System.currentTimeMillis(),
            title = title.trim(),
            completed = false,
            category = category,
            isImportant = isImportant
        )
        _todos.value = listOf(newItem) + _todos.value
    }

    override fun edit(id: Long, newTitle: String, category: String?, isImportant: Boolean?) {
        if (newTitle.isBlank()) return
        _todos.value = _todos.value.map { item ->
            if (item.id == id) {
                item.copy(
                    title = newTitle.trim(),
                    category = category,
                    isImportant = isImportant ?: item.isImportant
                )
            } else item
        }
    }

    override fun toggle(id: Long) {
        _todos.value = _todos.value.map { item ->
            if (item.id == id) item.copy(completed = !item.completed) else item
        }
    }

    override fun toggleImportant(id: Long) {
        _todos.value = _todos.value.map { item ->
            if (item.id == id) item.copy(isImportant = !item.isImportant) else item
        }
    }

    override fun delete(id: Long) {
        _todos.value = _todos.value.filterNot { it.id == id }
    }
}



