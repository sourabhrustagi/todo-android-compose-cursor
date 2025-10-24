package com.example.todoandroid.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

data class TutorialStep(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val color: Color,
    val tips: List<String> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTutorialScreen(
    onBack: () -> Unit = {},
    onComplete: () -> Unit = {}
) {
    val tutorialSteps = remember {
        listOf(
            TutorialStep(
                id = "1",
                title = "Welcome to Todo App",
                description = "Let's take a quick tour to help you get started with managing your tasks effectively.",
                icon = "👋",
                color = Color(0xFF6200EE),
                tips = listOf(
                    "This tutorial will show you the main features",
                    "You can skip anytime by tapping the skip button",
                    "Each step builds on the previous one"
                )
            ),
            TutorialStep(
                id = "2",
                title = "Creating Your First Task",
                description = "Tap the '+' button to create a new task. Add a title, description, and set a category to keep things organized.",
                icon = "➕",
                color = Color(0xFF03DAC6),
                tips = listOf(
                    "Always give your tasks clear, descriptive titles",
                    "Use categories to group related tasks",
                    "Set due dates for time-sensitive tasks"
                )
            ),
            TutorialStep(
                id = "3",
                title = "Marking Tasks as Important",
                description = "Use the star icon to mark important tasks. These will be highlighted in your task list for easy identification.",
                icon = "⭐",
                color = Color(0xFFFF9800),
                tips = listOf(
                    "Mark urgent tasks as important",
                    "Important tasks appear at the top of your list",
                    "Use this feature sparingly for maximum impact"
                )
            ),
            TutorialStep(
                id = "4",
                title = "Tracking Your Progress",
                description = "Check your statistics to see how many tasks you've completed. Track your productivity and stay motivated!",
                icon = "📊",
                color = Color(0xFF4CAF50),
                tips = listOf(
                    "View your completion rate in Statistics",
                    "Track your daily and weekly progress",
                    "Celebrate your achievements!"
                )
            ),
            TutorialStep(
                id = "5",
                title = "Using the Navigation Drawer",
                description = "Swipe from the left or tap the menu icon to access Settings, Statistics, and other features.",
                icon = "📱",
                color = Color(0xFF018786),
                tips = listOf(
                    "Access Settings to customize your experience",
                    "View Statistics to track your progress",
                    "Log out when you're done"
                )
            ),
            TutorialStep(
                id = "6",
                title = "You're All Set!",
                description = "You now know the basics of using Todo App. Start creating tasks and stay productive!",
                icon = "🎉",
                color = Color(0xFF6200EE),
                tips = listOf(
                    "Don't forget to backup your data",
                    "Check out the FAQ if you have questions",
                    "Happy task managing!"
                )
            )
        )
    }

    var currentStep by remember { mutableStateOf(0) }
    var showTips by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tutorial") },
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
            // Progress Indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Step ${currentStep + 1} of ${tutorialSteps.size}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                TextButton(
                    onClick = { onComplete() }
                ) {
                    Text("Skip Tutorial")
                }
            }

            // Progress Bar
            androidx.compose.material3.LinearProgressIndicator(
                progress = (currentStep + 1).toFloat() / tutorialSteps.size,
                modifier = Modifier.fillMaxWidth()
            )

            // Tutorial Content
            val currentTutorial = tutorialSteps[currentStep]
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = currentTutorial.color.copy(alpha = 0.1f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Icon
                    Card(
                        modifier = Modifier.size(80.dp),
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = currentTutorial.color.copy(alpha = 0.2f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = currentTutorial.icon,
                                style = MaterialTheme.typography.displayMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Title
                    Text(
                        text = currentTutorial.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = currentTutorial.color
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Description
                    Text(
                        text = currentTutorial.description,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Tips Section
                    if (currentTutorial.tips.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Button(
                            onClick = { showTips = !showTips },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = currentTutorial.color.copy(alpha = 0.2f)
                            )
                        ) {
                            Icon(
                                imageVector = if (showTips) Icons.Default.CheckCircle else Icons.Default.PlayArrow,
                                contentDescription = "Tips",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (showTips) "Hide Tips" else "Show Tips")
                        }

                        if (showTips) {
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = "💡 Pro Tips:",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = currentTutorial.color
                                    )
                                    
                                    Spacer(modifier = Modifier.height(8.dp))
                                    
                                    currentTutorial.tips.forEach { tip ->
                                        Row(
                                            verticalAlignment = Alignment.Top
                                        ) {
                                            Text(
                                                text = "• ",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = currentTutorial.color
                                            )
                                            Text(
                                                text = tip,
                                                style = MaterialTheme.typography.bodyMedium,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Navigation Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Previous Button
                if (currentStep > 0) {
                    Button(
                        onClick = { currentStep-- },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Previous",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Previous")
                    }
                } else {
                    Spacer(modifier = Modifier.width(100.dp))
                }

                // Next/Complete Button
                Button(
                    onClick = {
                        if (currentStep < tutorialSteps.size - 1) {
                            currentStep++
                        } else {
                            onComplete()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = currentTutorial.color
                    )
                ) {
                    Text(
                        text = if (currentStep < tutorialSteps.size - 1) "Next" else "Complete",
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Next",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
