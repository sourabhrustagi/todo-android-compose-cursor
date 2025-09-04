package com.example.todoandroidcursor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoandroidcursor.ui.theme.TodoAndroidCursorTheme
import com.example.todoandroidcursor.viewmodel.TodoViewModel
import com.example.todoandroidcursor.model.Todo

// Using model.Todo from model layer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAndroidCursorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TodoApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TodoApp(modifier: Modifier = Modifier) {
    val viewModel: TodoViewModel = viewModel(factory = TodoViewModel.factory())
    val todos = viewModel.todos.collectAsState()

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Todo",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TodoInput(onAdd = { title -> viewModel.addTodo(title) })

        Spacer(modifier = Modifier.padding(8.dp))
        Divider()
        Spacer(modifier = Modifier.padding(8.dp))

        TodoList(
            items = todos.value,
            onToggle = { id -> viewModel.toggleTodo(id) },
            onDelete = { id -> viewModel.deleteTodo(id) }
        )
    }
}

@Composable
fun TodoInput(onAdd: (String) -> Unit, modifier: Modifier = Modifier) {
    val (text, setText) = remember { mutableStateOf("") }
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        TextField(
            value = text,
            onValueChange = setText,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Add a task") }
        )
        Button(onClick = {
            onAdd(text)
            setText("")
        }) {
            Text("Add")
        }
    }
}

@Composable
fun TodoList(
    items: List<Todo>,
    onToggle: (Long) -> Unit,
    onDelete: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    if (items.isEmpty()) {
        Text(
            text = "No tasks yet",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(top = 16.dp)
        )
        return
    }

    LazyColumn(modifier = modifier.fillMaxSize()) {
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
    onDelete: (Long) -> Unit,
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
        TextButton(onClick = { onDelete(item.id) }) {
            Text("Delete")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoPreview() {
    TodoAndroidCursorTheme {
        TodoApp()
    }
}