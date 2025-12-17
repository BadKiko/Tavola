package com.kiko.tavola.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PhotoLibrary
import com.kiko.tavola.domain.model.Recipe
import com.kiko.tavola.domain.model.Difficulty

/**
 * Карточка рецепта в expressive стиле Material 3
 * Использует ElevatedCard + чипы + оверлейные действия
 */
@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium, // 16dp скругление
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            Box {
                RecipeImage(
                    imagePath = recipe.imagePath,
                    contentDescription = recipe.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                    contentScale = ContentScale.Crop
                )

                FilledIconButton(
                    onClick = { /* TODO: избранное */ },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = "Добавить в избранное"
                    )
                }

                if (recipe.imageUrls.size > 1) {
                    BadgedBox(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp),
                        badge = {
                            Badge {
                                Text("+${recipe.imageUrls.size - 1}")
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PhotoLibrary,
                            contentDescription = "Несколько фото"
                        )
                    }
                }
            }
            
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AssistChip(
                        onClick = { /* ничего */ },
                        label = { Text("${recipe.cookingTime} мин") },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                    AssistChip(
                        onClick = { /* ничего */ },
                        label = {
                            Text(
                                when (recipe.difficulty) {
                                    Difficulty.EASY -> "Легко"
                                    Difficulty.MEDIUM -> "Средне"
                                    Difficulty.HARD -> "Сложно"
                                }
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }
        }
    }
}

