package com.example.todoandroid.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoandroid.model.Todo
import com.example.todoandroid.ui.components.CategorySelectionComponent
import com.example.todoandroid.ui.components.LogoutConfirmationDialog
import com.example.todoandroid.utils.TaskCategories
import com.example.todoandroid.viewmodel.TodoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    onProfileClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onStatsClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onFeedbackClick: () -> Unit = {},
    onFAQClick: () -> Unit = {},
    onAchievementsClick: () -> Unit = {},
    onChangelogClick: () -> Unit = {},
    onBackupSyncClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {},
    onAddTaskClick: () -> Unit = {}
) {
    val viewModel: TodoViewModel = hiltViewModel()
    val todos = viewModel.todos.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditTaskDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var todoToDelete by remember { mutableStateOf<Todo?>(null) }
    var todoToEdit by remember { mutableStateOf<Todo?>(null) }
    var isImportant by remember { mutableStateOf(false) }
    var isScreenActive by remember { mutableStateOf(true) }

    val todosList = todos.value
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    
    // Handle drawer state changes and ensure proper state management
    androidx.compose.runtime.LaunchedEffect(drawerState.isOpen) {
        // Ensure drawer state is properly managed
        if (drawerState.isOpen) {
            android.util.Log.d("TodoScreen", "Drawer opened")
        } else {
            android.util.Log.d("TodoScreen", "Drawer closed")
        }
    }
    
    // Reset drawer state when screen becomes visible (e.g., returning from settings)
    androidx.compose.runtime.LaunchedEffect(Unit) {
        // Ensure drawer is closed when screen is first composed or recomposed
        try {
            if (drawerState.isOpen) {
                drawerState.close()
            }
        } catch (e: Exception) {
            android.util.Log.e("TodoScreen", "Error resetting drawer state", e)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Todo App",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Star, contentDescription = "Statistics") },
                        label = { Text("Statistics") },
                        selected = false,
                        onClick = {
                            scope.launch { 
                                drawerState.close()
                                onStatsClick()
                            }
                        }
                    )
                    
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                        label = { Text("Settings") },
                        selected = false,
                        onClick = {
                            scope.launch { 
                                drawerState.close()
                                onSettingsClick()
                            }
                        }
                    )
                    
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                        label = { Text("Profile") },
                        selected = false,
                        onClick = {
                            scope.launch { 
                                drawerState.close()
                                onProfileClick()
                            }
                        }
                    )
                    
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = "FAQ") },
                        label = { Text("FAQ") },
                        selected = false,
                        onClick = {
                            scope.launch { 
                                drawerState.close()
                                onFAQClick()
                            }
                        }
                    )
                    
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Star, contentDescription = "Achievements") },
                        label = { Text("Achievements") },
                        selected = false,
                        onClick = {
                            scope.launch { 
                                drawerState.close()
                                onAchievementsClick()
                            }
                        }
                    )
                    
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Changelog") },
                        label = { Text("Changelog") },
                        selected = false,
                        onClick = {
                            scope.launch { 
                                drawerState.close()
                                onChangelogClick()
                            }
                        }
                    )
                    
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Backup & Sync") },
                        label = { Text("Backup & Sync") },
                        selected = false,
                        onClick = {
                            scope.launch { 
                                drawerState.close()
                                onBackupSyncClick()
                            }
                        }
                    )
                    
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Privacy Policy") },
                        label = { Text("Privacy Policy") },
                        selected = false,
                        onClick = {
                            scope.launch { 
                                drawerState.close()
                                onPrivacyPolicyClick()
                            }
                        }
                    )
                    
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Feedback") },
                        label = { Text("Feedback") },
                        selected = false,
                        onClick = {
                            scope.launch { 
                                drawerState.close()
                                onFeedbackClick()
                            }
                        }
                    )
                    
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))
                    
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = "Logout") },
                        label = { Text("Logout") },
                        selected = false,
                        onClick = {
                            scope.launch { 
                                drawerState.close()
                                showLogoutDialog = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { 
                        Text(
                            "My Tasks",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        ) 
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { 
                                scope.launch { 
                                    try {
                                        if (drawerState.isOpen) {
                                            drawerState.close()
                                        } else {
                                            drawerState.open()
                                        }
                                    } catch (e: Exception) {
                                        android.util.Log.e("TodoScreen", "Error handling drawer state", e)
                                        // Reset drawer state if there's an error
                                        drawerState.close()
                                    }
                                } 
                            }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        // Profile button removed for cleaner UI
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onAddTaskClick() }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Task")
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
                    onToggleImportant = { id -> viewModel.toggleImportant(id) },
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
    }

    // Edit task dialog
    if (showEditTaskDialog && todoToEdit != null) {
        EditTaskDialog(
            todo = todoToEdit!!,
            onEdit = { newTitle, category, important ->
                viewModel.editTodo(todoToEdit!!.id, newTitle, category, important)
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
    
    if (showLogoutDialog) {
        LogoutConfirmationDialog(
            onConfirm = {
                showLogoutDialog = false
                onLogoutClick()
            },
            onDismiss = {
                showLogoutDialog = false
            }
        )
    }
}


@Composable
fun TodoList(
    items: List<Todo>,
    onToggle: (Long) -> Unit,
    onToggleImportant: (Long) -> Unit,
    onDelete: (Todo) -> Unit,
    onEdit: (Todo) -> Unit,
    modifier: Modifier = Modifier
) {
    if (items.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "No tasks",
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
                Text(
                    text = "No tasks yet",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                )
                Text(
                    text = "Tap the + button to add your first task",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
        return
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items, key = { it.id }) { item ->
            TodoRow(
                item = item, 
                onToggle = onToggle, 
                onToggleImportant = onToggleImportant,
                onDelete = onDelete, 
                onEdit = onEdit
            )
        }
    }
}

@Composable
fun TodoRow(
    item: Todo,
    onToggle: (Long) -> Unit,
    onToggleImportant: (Long) -> Unit,
    onDelete: (Todo) -> Unit,
    onEdit: (Todo) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (item.isImportant) {
                MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = item.completed, 
                        onCheckedChange = { onToggle(item.id) },
                        colors = androidx.compose.material3.CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.primary,
                            uncheckedColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = item.title,
                                style = if (item.completed) {
                                    MaterialTheme.typography.bodyLarge.copy(
                                        textDecoration = TextDecoration.LineThrough,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                } else {
                                    MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                                    )
                                }
                            )
                            if (item.isImportant) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Important",
                                    modifier = Modifier.size(18.dp),
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }

                        // Category chip
                        if (item.category != null) {
                            Row(
                                modifier = Modifier.padding(top = 6.dp)
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
                                            .copy(alpha = 0.2f),
                                        labelColor = TaskCategories.getCategoryColor(item.category)
                                    )
                                )
                            }
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(
                        onClick = { onToggleImportant(item.id) },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Toggle Important",
                            tint = if (item.isImportant) {
                                MaterialTheme.colorScheme.error
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            },
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    TextButton(
                        onClick = { onEdit(item) },
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(
                            "Edit",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    TextButton(
                        onClick = { onDelete(item) },
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(
                            "Delete",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EditTaskDialog(
    todo: Todo,
    onEdit: (String, String?, Boolean?) -> Unit,
    onDismiss: () -> Unit
) {
    var taskText by remember { mutableStateOf(todo.title) }
    var selectedCategory by remember { mutableStateOf(todo.category) }
    var isImportant by remember { mutableStateOf(todo.isImportant) }

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
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isImportant,
                        onCheckedChange = { isImportant = it }
                    )
                    Text("Mark as important")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (taskText.isNotBlank() && (taskText.trim() != todo.title || selectedCategory != todo.category || isImportant != todo.isImportant)) {
                        onEdit(taskText.trim(), selectedCategory, isImportant)
                    } else {
                        onDismiss()
                    }
                },
                enabled = taskText.isNotBlank(),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    "Save",
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                )
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
                onClick = onConfirm,
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text(
                    "Delete",
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                )
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

