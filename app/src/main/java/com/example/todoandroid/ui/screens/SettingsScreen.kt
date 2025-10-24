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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todoandroid.ui.theme.ThemeMode
import com.example.todoandroid.ui.theme.LocalThemeManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {},
    onBackupSyncClick: () -> Unit = {},
    onAchievementsClick: () -> Unit = {},
    onFAQClick: () -> Unit = {},
    onTutorialClick: () -> Unit = {},
    onChangelogClick: () -> Unit = {}
) {
    var currentTheme by remember { mutableStateOf(ThemeMode.SYSTEM) }
    var notificationsEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Settings",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    ) 
                },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "App Settings",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            // Theme Selection Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Theme",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Theme",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    Column {
                        ThemeMode.values().forEach { mode ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = currentTheme == mode,
                                    onClick = {
                                        currentTheme = mode
                                        // Theme change will be handled by the parent
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = when (mode) {
                                        ThemeMode.LIGHT -> Icons.Default.Star
                                        ThemeMode.DARK -> Icons.Default.Person
                                        ThemeMode.SYSTEM -> Icons.Default.Settings
                                    },
                                    contentDescription = mode.name,
                                    modifier = Modifier.size(20.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = when (mode) {
                                        ThemeMode.LIGHT -> "Light"
                                        ThemeMode.DARK -> "Dark"
                                        ThemeMode.SYSTEM -> "System Default"
                                    },
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            // Notifications Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = if (notificationsEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Notifications",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Push Notifications",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = if (notificationsEnabled) "You'll receive reminders for your todos" else "Notifications are disabled",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.primary,
                                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                                uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        )
                    }
                }
            }

            // Coming Soon Features Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Coming Soon",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Coming Soon",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    // Feature 1: Cloud Sync
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Cloud Sync",
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Cloud Sync",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Sync your todos across devices",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = "Soon",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    // Feature 2: Share Todos
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share Todos",
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Share Todos",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Share your todo lists with friends",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = "Soon",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    // Feature 3: Smart Reminders
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Smart Reminders",
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Smart Reminders",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "AI-powered reminder suggestions",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = "Soon",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

                   // App Features Card
                   Card(
                       modifier = Modifier.fillMaxWidth(),
                       elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                   ) {
                       Column(
                           modifier = Modifier.padding(20.dp)
                       ) {
                           Text(
                               text = "App Features",
                               style = MaterialTheme.typography.titleMedium,
                               fontWeight = FontWeight.Medium,
                               modifier = Modifier.padding(bottom = 12.dp)
                           )

                           // Backup & Sync
                           Row(
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(vertical = 8.dp),
                               verticalAlignment = Alignment.CenterVertically
                           ) {
                               Text(
                                   text = "Backup & Sync",
                                   style = MaterialTheme.typography.bodyLarge,
                                   modifier = Modifier.weight(1f)
                               )
                               TextButton(
                                   onClick = onBackupSyncClick
                               ) {
                                   Text(
                                       text = "Manage",
                                       style = MaterialTheme.typography.bodyMedium,
                                       color = MaterialTheme.colorScheme.primary,
                                       fontWeight = FontWeight.Medium
                                   )
                               }
                           }

                           // Achievements
                           Row(
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(vertical = 8.dp),
                               verticalAlignment = Alignment.CenterVertically
                           ) {
                               Text(
                                   text = "Achievements",
                                   style = MaterialTheme.typography.bodyLarge,
                                   modifier = Modifier.weight(1f)
                               )
                               TextButton(
                                   onClick = onAchievementsClick
                               ) {
                                   Text(
                                       text = "View",
                                       style = MaterialTheme.typography.bodyMedium,
                                       color = MaterialTheme.colorScheme.primary,
                                       fontWeight = FontWeight.Medium
                                   )
                               }
                           }

                           // FAQ
                           Row(
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(vertical = 8.dp),
                               verticalAlignment = Alignment.CenterVertically
                           ) {
                               Text(
                                   text = "FAQ",
                                   style = MaterialTheme.typography.bodyLarge,
                                   modifier = Modifier.weight(1f)
                               )
                               TextButton(
                                   onClick = onFAQClick
                               ) {
                                   Text(
                                       text = "View",
                                       style = MaterialTheme.typography.bodyMedium,
                                       color = MaterialTheme.colorScheme.primary,
                                       fontWeight = FontWeight.Medium
                                   )
                               }
                           }

                           // Tutorial
                           Row(
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(vertical = 8.dp),
                               verticalAlignment = Alignment.CenterVertically
                           ) {
                               Text(
                                   text = "Tutorial",
                                   style = MaterialTheme.typography.bodyLarge,
                                   modifier = Modifier.weight(1f)
                               )
                               TextButton(
                                   onClick = onTutorialClick
                               ) {
                                   Text(
                                       text = "Start",
                                       style = MaterialTheme.typography.bodyMedium,
                                       color = MaterialTheme.colorScheme.primary,
                                       fontWeight = FontWeight.Medium
                                   )
                               }
                           }

                           // Changelog
                           Row(
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(vertical = 8.dp),
                               verticalAlignment = Alignment.CenterVertically
                           ) {
                               Text(
                                   text = "Changelog",
                                   style = MaterialTheme.typography.bodyLarge,
                                   modifier = Modifier.weight(1f)
                               )
                               TextButton(
                                   onClick = onChangelogClick
                               ) {
                                   Text(
                                       text = "View",
                                       style = MaterialTheme.typography.bodyMedium,
                                       color = MaterialTheme.colorScheme.primary,
                                       fontWeight = FontWeight.Medium
                                   )
                               }
                           }
                       }
                   }

                   // Privacy Policy Card
                   Card(
                       modifier = Modifier.fillMaxWidth(),
                       elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                   ) {
                       Column(
                           modifier = Modifier.padding(20.dp)
                       ) {
                           Text(
                               text = "Legal",
                               style = MaterialTheme.typography.titleMedium,
                               fontWeight = FontWeight.Medium,
                               modifier = Modifier.padding(bottom = 12.dp)
                           )

                           Row(
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(vertical = 8.dp),
                               verticalAlignment = Alignment.CenterVertically
                           ) {
                               Text(
                                   text = "Privacy Policy",
                                   style = MaterialTheme.typography.bodyLarge,
                                   modifier = Modifier.weight(1f)
                               )
                               TextButton(
                                   onClick = onPrivacyPolicyClick
                               ) {
                                   Text(
                                       text = "View",
                                       style = MaterialTheme.typography.bodyMedium,
                                       color = MaterialTheme.colorScheme.primary,
                                       fontWeight = FontWeight.Medium
                                   )
                               }
                           }
                       }
                   }

            // App Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "App Information",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Version:",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "1.0.0",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Build:",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Debug",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
