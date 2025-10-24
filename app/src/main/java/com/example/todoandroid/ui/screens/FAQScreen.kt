package com.example.todoandroid.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class FAQItem(
    val id: String,
    val question: String,
    val answer: String,
    val category: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAQScreen(
    onBack: () -> Unit = {}
) {
    val faqItems = remember {
        listOf(
            FAQItem(
                id = "1",
                question = "How do I create a new task?",
                answer = "To create a new task, tap the '+' button on the main screen. Fill in the task title, add a description if needed, select a category, and choose the priority level. You can also mark it as important and set a due date.",
                category = "Getting Started"
            ),
            FAQItem(
                id = "2",
                question = "How do I mark a task as important?",
                answer = "You can mark a task as important by tapping the star icon next to the task, or by editing the task and checking the 'Mark as Important' option. Important tasks will be highlighted in your task list.",
                category = "Getting Started"
            ),
            FAQItem(
                id = "3",
                question = "Can I organize tasks by categories?",
                answer = "Yes! You can assign categories to your tasks when creating or editing them. Categories help you organize your tasks better. You can create custom categories like 'Work', 'Personal', 'Shopping', etc.",
                category = "Organization"
            ),
            FAQItem(
                id = "4",
                question = "How do I edit or delete a task?",
                answer = "To edit a task, tap the 'Edit' button next to the task. To delete a task, tap the 'Delete' button. You'll be asked to confirm before deleting.",
                category = "Organization"
            ),
            FAQItem(
                id = "5",
                question = "What are achievements?",
                answer = "Achievements are badges you earn by completing various tasks and maintaining good habits. They help motivate you to stay productive and organized. Check your achievements in the Statistics section.",
                category = "Features"
            ),
            FAQItem(
                id = "6",
                question = "How do I backup my data?",
                answer = "Go to Settings > Backup & Sync to manually backup your data or enable automatic backup. Your data will be securely stored in the cloud and can be restored if needed.",
                category = "Data & Sync"
            ),
            FAQItem(
                id = "7",
                question = "Can I sync my tasks across devices?",
                answer = "Yes! When you enable cloud sync, your tasks will be automatically synchronized across all your devices. Make sure you're signed in with the same account on all devices.",
                category = "Data & Sync"
            ),
            FAQItem(
                id = "8",
                question = "How do I change the app theme?",
                answer = "Go to Settings and look for the Theme section. You can choose between Light, Dark, or System default theme. The System option will follow your device's theme setting.",
                category = "Settings"
            ),
            FAQItem(
                id = "9",
                question = "What if I forget my password?",
                answer = "On the login screen, tap 'Forgot Password?' and follow the instructions to reset your password. You'll receive an email with a reset link.",
                category = "Account"
            ),
            FAQItem(
                id = "10",
                question = "How do I contact support?",
                answer = "You can contact our support team through the feedback form in your profile. We typically respond within 24 hours. You can also check our FAQ for common questions.",
                category = "Support"
            )
        )
    }

    val categories = faqItems.groupBy { it.category }
    var expandedItems by remember { mutableStateOf(setOf<String>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FAQ") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "FAQ",
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Frequently Asked Questions",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Find answers to common questions about using the Todo app",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            // FAQ Items by Category
            categories.forEach { (category, items) ->
                item {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                
                items.forEach { faqItem ->
                    item {
                        FAQCard(
                            faqItem = faqItem,
                            isExpanded = expandedItems.contains(faqItem.id),
                            onToggleExpanded = {
                                expandedItems = if (expandedItems.contains(faqItem.id)) {
                                    expandedItems - faqItem.id
                                } else {
                                    expandedItems + faqItem.id
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FAQCard(
    faqItem: FAQItem,
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Question Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = faqItem.question,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                
                IconButton(
                    onClick = onToggleExpanded
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.Star else Icons.Default.Settings,
                        contentDescription = if (isExpanded) "Collapse" else "Expand"
                    )
                }
            }
            
            // Answer (shown when expanded)
            if (isExpanded) {
                Text(
                    text = faqItem.answer,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                )
            }
        }
    }
}
