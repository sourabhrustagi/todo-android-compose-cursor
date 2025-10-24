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
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class ChangelogEntry(
    val version: String,
    val date: String,
    val type: ChangelogType,
    val title: String,
    val description: String
)

enum class ChangelogType(
    val icon: ImageVector,
    val color: Color,
    val label: String
) {
    NEW_FEATURE(
        icon = Icons.Default.Settings,
        color = Color(0xFF4CAF50),
        label = "New Feature"
    ),
    IMPROVEMENT(
        icon = Icons.Default.Settings,
        color = Color(0xFF2196F3),
        label = "Improvement"
    ),
    BUG_FIX(
        icon = Icons.Default.Warning,
        color = Color(0xFFFF9800),
        label = "Bug Fix"
    ),
    BREAKING_CHANGE(
        icon = Icons.Default.Warning,
        color = Color(0xFFF44336),
        label = "Breaking Change"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangelogScreen(
    onBack: () -> Unit = {}
) {
    val changelogEntries = remember {
        listOf(
            ChangelogEntry(
                version = "1.2.0",
                date = "December 15, 2024",
                type = ChangelogType.NEW_FEATURE,
                title = "Achievements System",
                description = "Added a comprehensive achievements system to motivate users and track their productivity milestones."
            ),
            ChangelogEntry(
                version = "1.2.0",
                date = "December 15, 2024",
                type = ChangelogType.NEW_FEATURE,
                title = "Data Backup & Sync",
                description = "Introduced cloud backup and sync functionality to keep your data safe and accessible across devices."
            ),
            ChangelogEntry(
                version = "1.2.0",
                date = "December 15, 2024",
                type = ChangelogType.NEW_FEATURE,
                title = "User Tutorial",
                description = "Added an interactive tutorial to help new users get started with the app quickly."
            ),
            ChangelogEntry(
                version = "1.1.5",
                date = "December 10, 2024",
                type = ChangelogType.IMPROVEMENT,
                title = "Enhanced Task Management",
                description = "Improved the Add/Edit Task screen with better categorization and priority settings."
            ),
            ChangelogEntry(
                version = "1.1.5",
                date = "December 10, 2024",
                type = ChangelogType.IMPROVEMENT,
                title = "Theme Customization",
                description = "Enhanced theme selection with better support for system theme and improved dark mode."
            ),
            ChangelogEntry(
                version = "1.1.4",
                date = "December 5, 2024",
                type = ChangelogType.BUG_FIX,
                title = "Login Screen Fix",
                description = "Fixed issue where password visibility toggle was not working properly on some devices."
            ),
            ChangelogEntry(
                version = "1.1.4",
                date = "December 5, 2024",
                type = ChangelogType.BUG_FIX,
                title = "Navigation Improvements",
                description = "Resolved navigation issues when switching between different screens rapidly."
            ),
            ChangelogEntry(
                version = "1.1.3",
                date = "November 28, 2024",
                type = ChangelogType.IMPROVEMENT,
                title = "Statistics Enhancement",
                description = "Improved the statistics screen with better visualizations and more detailed progress tracking."
            ),
            ChangelogEntry(
                version = "1.1.3",
                date = "November 28, 2024",
                type = ChangelogType.NEW_FEATURE,
                title = "FAQ Section",
                description = "Added a comprehensive FAQ section to help users find answers to common questions."
            ),
            ChangelogEntry(
                version = "1.1.2",
                date = "November 20, 2024",
                type = ChangelogType.IMPROVEMENT,
                title = "Performance Optimization",
                description = "Optimized app performance and reduced memory usage for better overall experience."
            ),
            ChangelogEntry(
                version = "1.1.1",
                date = "November 15, 2024",
                type = ChangelogType.BUG_FIX,
                title = "Crash Fix",
                description = "Fixed a crash that occurred when editing tasks with special characters in the title."
            ),
            ChangelogEntry(
                version = "1.1.0",
                date = "November 10, 2024",
                type = ChangelogType.NEW_FEATURE,
                title = "Signup Functionality",
                description = "Added user registration functionality with email verification and password reset features."
            ),
            ChangelogEntry(
                version = "1.1.0",
                date = "November 10, 2024",
                type = ChangelogType.NEW_FEATURE,
                title = "Settings Screen",
                description = "Introduced a comprehensive settings screen with theme selection and notification preferences."
            ),
            ChangelogEntry(
                version = "1.0.5",
                date = "November 5, 2024",
                type = ChangelogType.IMPROVEMENT,
                title = "UI Improvements",
                description = "Enhanced the overall user interface with better Material Design 3 components and animations."
            ),
            ChangelogEntry(
                version = "1.0.4",
                date = "October 30, 2024",
                type = ChangelogType.BUG_FIX,
                title = "Data Persistence",
                description = "Fixed issue where task data was not being saved properly after app restart."
            ),
            ChangelogEntry(
                version = "1.0.3",
                date = "October 25, 2024",
                type = ChangelogType.IMPROVEMENT,
                title = "Task Priority",
                description = "Added ability to mark tasks as important with visual indicators in the task list."
            ),
            ChangelogEntry(
                version = "1.0.2",
                date = "October 20, 2024",
                type = ChangelogType.BUG_FIX,
                title = "Login Issues",
                description = "Resolved authentication issues that prevented some users from logging in successfully."
            ),
            ChangelogEntry(
                version = "1.0.1",
                date = "October 15, 2024",
                type = ChangelogType.IMPROVEMENT,
                title = "Initial Release",
                description = "First stable release of Todo App with core task management functionality."
            ),
            ChangelogEntry(
                version = "1.0.0",
                date = "October 10, 2024",
                type = ChangelogType.NEW_FEATURE,
                title = "App Launch",
                description = "Welcome to Todo App! The first version with basic task creation, editing, and completion features."
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Changelog") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
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
                        contentDescription = "Changelog",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "What's New",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Stay updated with the latest features, improvements, and bug fixes",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            // Changelog Entries
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(changelogEntries) { entry ->
                    ChangelogCard(entry = entry)
                }
            }
        }
    }
}

@Composable
fun ChangelogCard(entry: ChangelogEntry) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = entry.type.icon,
                        contentDescription = entry.type.label,
                        tint = entry.type.color,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = entry.type.label,
                        style = MaterialTheme.typography.labelMedium,
                        color = entry.type.color,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Text(
                    text = entry.version,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Title
            Text(
                text = entry.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Description
            Text(
                text = entry.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Date
            Text(
                text = entry.date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
