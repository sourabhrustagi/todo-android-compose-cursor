package com.example.todoandroidcursor.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoandroidcursor.model.Todo
import com.example.todoandroidcursor.viewmodel.TodoViewModel

@Composable
fun TodoScreen(
    onLogout: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val viewModel: TodoViewModel = hiltViewModel()
    val todos = viewModel.todos.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showAddTaskDialog by remember { mutableStateOf(false) }
    var todoToDelete by remember { mutableStateOf<Todo?>(null) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Todo",
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(onClick = onProfileClick) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddTaskDialog = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TodoList(
                items = todos.value,
                onToggle = { id -> viewModel.toggleTodo(id) },
                onDelete = { todo ->
                    todoToDelete = todo
                    showDeleteDialog = true
                }
            )
        }
    }

    // Add task dialog
    if (showAddTaskDialog) {
        AddTaskDialog(
            onAdd = { title ->
                viewModel.addTodo(title)
                showAddTaskDialog = false
            },
            onDismiss = {
                showAddTaskDialog = false
            }
        )
    }

    // Delete confirmation dialog
    if (showDeleteDialog && todoToDelete != null) {
        DeleteConfirmationDialog(
            todo = todoToDelete!!,
            onConfirm = {
                viewModel.deleteTodo(todoToDelete!!.id)
                showDeleteDialog = false
                todoToDelete = null
            },
            onDismiss = {
                showDeleteDialog = false
                todoToDelete = null
            }
        )
    }
}


@Composable
fun TodoList(
    items: List<Todo>,
    onToggle: (Long) -> Unit,
    onDelete: (Todo) -> Unit,
    modifier: Modifier = Modifier
) {
    if (items.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No tasks yet",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        return
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
    ) {
        items(items, key = { it.id }) { item ->
            TodoRow(item = item, onToggle = onToggle, onDelete = onDelete)
            Divider()
        }
    }
}

@Composable
fun TodoRow(
    item: Todo,
    onToggle: (Long) -> Unit,
    onDelete: (Todo) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
            Checkbox(checked = item.completed, onCheckedChange = { onToggle(item.id) })
            Text(
                text = item.title,
                style = if (item.completed) {
                    MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.LineThrough)
                } else {
                    MaterialTheme.typography.bodyLarge
                }
            )
        }
        TextButton(onClick = { onDelete(item) }) {
            Text("Delete")
        }
    }
}

@Composable
fun AddTaskDialog(
    onAdd: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var taskText by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Add New Task")
        },
        text = {
            TextField(
                value = taskText,
                onValueChange = { taskText = it },
                placeholder = { Text("Enter task description") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    if (taskText.isNotBlank()) {
                        onAdd(taskText.trim())
                        taskText = ""
                    }
                },
                enabled = taskText.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    taskText = ""
                    onDismiss()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun DeleteConfirmationDialog(
    todo: Todo,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Delete Todo")
        },
        text = {
            Text("Are you sure you want to delete \"${todo.title}\"?")
        },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}



