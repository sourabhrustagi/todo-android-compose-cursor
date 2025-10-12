package com.example.todoandroid.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todoandroid.utils.TaskCategories
import com.example.todoandroid.utils.TaskCategory

@Composable
fun CategorySelectionComponent(
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Category",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium
        )
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // No Category option
                AssistChip(
                    onClick = { onCategorySelected(null) },
                    label = { Text("No Category") },
                    modifier = Modifier.wrapContentWidth(),
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (selectedCategory == null) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    )
                )
                
                // Category chips
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(TaskCategories.ALL_CATEGORIES) { category ->
                        CategoryChip(
                            category = category,
                            isSelected = selectedCategory == category.name,
                            onClick = { onCategorySelected(category.name) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryChip(
    category: TaskCategory,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AssistChip(
        onClick = onClick,
        label = { 
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(category.icon)
                Text(category.displayName)
            }
        },
        modifier = modifier,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (isSelected) {
                category.color.copy(alpha = 0.3f)
            } else {
                MaterialTheme.colorScheme.surface
            },
            labelColor = if (isSelected) {
                category.color
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        ),
        shape = RoundedCornerShape(16.dp)
    )
}
