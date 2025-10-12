package com.example.todoandroid.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import com.example.todoandroid.model.Todo
import com.example.todoandroid.ui.components.CategorySelectionComponent
import com.example.todoandroid.utils.TaskCategories
import com.example.todoandroid.viewmodel.TodoViewModel

@Composable
fun TodoScreen(
    onProfileClick: () -> Unit = {}
) {
    val viewModel: TodoViewModel = hiltViewModel()
    val todos = viewModel.todos.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showAddTaskDialog by remember { mutableStateOf(false) }
    var showEditTaskDialog by remember { mutableStateOf(false) }
    var todoToDelete by remember { mutableStateOf<Todo?>(null) }
    var todoToEdit by remember { mutableStateOf<Todo?>(null) }

    val todosList = todos.value

    Scaffold(
        topBar = {
            Column {
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
                items = todosList,
                onToggle = { id -> viewModel.toggleTodo(id) },
                onDelete = { todo ->
                    todoToDelete = todo
                    showDeleteDialog = true
                },
                onEdit = { todo ->
                    todoToEdit = todo
                    showEditTaskDialog = true
                }
            )
        }
    }

    // Add task dialog
    if (showAddTaskDialog) {
        AddTaskDialog(
            onAdd = { title, category ->
                viewModel.addTodo(title, category)
                showAddTaskDialog = false
            },
            onDismiss = {
                showAddTaskDialog = false
            }
        )
    }

    // Edit task dialog
    if (showEditTaskDialog && todoToEdit != null) {
        EditTaskDialog(
            todo = todoToEdit!!,
            onEdit = { newTitle, category ->
                viewModel.editTodo(todoToEdit!!.id, newTitle, category)
                showEditTaskDialog = false
                todoToEdit = null
            },
            onDismiss = {
                showEditTaskDialog = false
                todoToEdit = null
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
    onEdit: (Todo) -> Unit,
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
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items, key = { it.id }) { item ->
            TodoRow(item = item, onToggle = onToggle, onDelete = onDelete, onEdit = onEdit)
        }
    }
}

@Composable
fun TodoRow(
    item: Todo,
    onToggle: (Long) -> Unit,
    onDelete: (Todo) -> Unit,
    onEdit: (Todo) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = item.completed, onCheckedChange = { onToggle(item.id) })
                    Column {
                        Text(
                            text = item.title,
                            style = if (item.completed) {
                                MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.LineThrough)
                            } else {
                                MaterialTheme.typography.bodyLarge
                            }
                        )

                        // Category chip
                        if (item.category != null) {
                            Row(
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                AssistChip(
                                    onClick = { },
                                    label = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Text(TaskCategories.getCategoryIcon(item.category))
                                            Text(TaskCategories.getCategoryDisplayName(item.category))
                                        }
                                    },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = TaskCategories.getCategoryColor(item.category)
                                            .copy(alpha = 0.3f),
                                        labelColor = TaskCategories.getCategoryColor(item.category)
                                    )
                                )
                            }
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(onClick = { onEdit(item) }) {
                        Text("Edit")
                    }
                    TextButton(onClick = { onDelete(item) }) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}

@Composable
fun AddTaskDialog(
    onAdd: (String, String?) -> Unit,
    onDismiss: () -> Unit
) {
    var taskText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Add New Task")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = taskText,
                    onValueChange = { taskText = it },
                    placeholder = { Text("Enter task description") },
                    modifier = Modifier.fillMaxWidth()
                )

                CategorySelectionComponent(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (taskText.isNotBlank()) {
                        onAdd(taskText.trim(), selectedCategory)
                        taskText = ""
                        selectedCategory = null
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
                    selectedCategory = null
                    onDismiss()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun EditTaskDialog(
    todo: Todo,
    onEdit: (String, String?) -> Unit,
    onDismiss: () -> Unit
) {
    var taskText by remember { mutableStateOf(todo.title) }
    var selectedCategory by remember { mutableStateOf(todo.category) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Edit Task")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = taskText,
                    onValueChange = { taskText = it },
                    placeholder = { Text("Enter task description") },
                    modifier = Modifier.fillMaxWidth()
                )

                CategorySelectionComponent(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (taskText.isNotBlank() && (taskText.trim() != todo.title || selectedCategory != todo.category)) {
                        onEdit(taskText.trim(), selectedCategory)
                    } else {
                        onDismiss()
                    }
                },
                enabled = taskText.isNotBlank()
            ) {
                Text("Save")
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



