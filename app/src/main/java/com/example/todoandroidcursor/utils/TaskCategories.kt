package com.example.todoandroidcursor.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Immutable

@Immutable
data class TaskCategory(
    val name: String,
    val displayName: String,
    val color: Color,
    val icon: String
)

object TaskCategories {
    val WORK = TaskCategory(
        name = "work",
        displayName = "Work",
        color = Color(0xFF2196F3), // Blue
        icon = "💼"
    )
    
    val PERSONAL = TaskCategory(
        name = "personal",
        displayName = "Personal",
        color = Color(0xFF4CAF50), // Green
        icon = "👤"
    )
    
    val SHOPPING = TaskCategory(
        name = "shopping",
        displayName = "Shopping",
        color = Color(0xFFFF9800), // Orange
        icon = "🛒"
    )
    
    val HEALTH = TaskCategory(
        name = "health",
        displayName = "Health",
        color = Color(0xFFE91E63), // Pink
        icon = "🏥"
    )
    
    val ALL_CATEGORIES = listOf(WORK, PERSONAL, SHOPPING, HEALTH)
    
    fun getCategoryByName(name: String?): TaskCategory? {
        return ALL_CATEGORIES.find { it.name == name }
    }
    
    fun getCategoryDisplayName(name: String?): String {
        return getCategoryByName(name)?.displayName ?: "No Category"
    }
    
    fun getCategoryColor(name: String?): Color {
        return getCategoryByName(name)?.color ?: Color.Gray
    }
    
    fun getCategoryIcon(name: String?): String {
        return getCategoryByName(name)?.icon ?: "📝"
    }
}
