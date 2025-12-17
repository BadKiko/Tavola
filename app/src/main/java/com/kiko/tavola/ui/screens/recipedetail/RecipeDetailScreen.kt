package com.kiko.tavola.ui.screens.recipedetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ListItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kiko.tavola.domain.model.Difficulty
import com.kiko.tavola.ui.components.RecipeImage
import com.kiko.tavola.ui.viewmodel.RecipeDetailViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RecipeDetailScreen(
    recipeId: String,
    onBack: () -> Unit,
    viewModel: RecipeDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(recipeId) {
        viewModel.loadRecipe(recipeId)
    }
    
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Рецепт") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Поделиться */ }) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Поделиться"
                        )
                    }
                    IconButton(onClick = { /* TODO: Избранное */ }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "В избранное"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                LoadingIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                uiState.recipe?.let { recipe ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 24.dp)
                    ) {
                        // Hero: если фото несколько — используем M3 Carousel
                        item {
                            if (recipe.imageUrls.size > 1) {
                                val carouselState = rememberCarouselState { recipe.imageUrls.size }
                                HorizontalUncontainedCarousel(
                                    state = carouselState,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp),
                                    itemWidth = 280.dp,
                                    itemSpacing = 12.dp
                                ) { index ->
                                    val url = recipe.imageUrls[index]
                                    RecipeImage(
                                        imageUrl = url,
                                        contentDescription = "${recipe.title} - фото ${index + 1}",
                                        modifier = Modifier
                                            .width(280.dp)
                                            .height(320.dp)
                                            .maskClip(MaterialTheme.shapes.extraLarge),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            } else {
                                RecipeImage(
                                    imagePath = recipe.imagePath,
                                    contentDescription = recipe.title,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        
                        // Основная информация
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(
                                    text = recipe.title,
                                    style = MaterialTheme.typography.headlineLarge
                                )

                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    contentPadding = PaddingValues(vertical = 4.dp)
                                ) {
                                    item {
                                        AssistChip(
                                            onClick = { /* ничего */ },
                                            label = { Text("${recipe.cookingTime} мин") },
                                            colors = AssistChipDefaults.assistChipColors(
                                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                                            )
                                        )
                                    }
                                    item {
                                        AssistChip(
                                            onClick = { /* ничего */ },
                                            label = { Text("${recipe.servings} порц.") },
                                            colors = AssistChipDefaults.assistChipColors(
                                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                                            )
                                        )
                                    }
                                    item {
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
                                
                                Text(
                                    text = recipe.description,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            }
                        }
                        
                        // Ингредиенты
                        item {
                            Text(
                                text = "Ингредиенты",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                            )
                        }
                        item {
                            ElevatedCard(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(),
                                shape = MaterialTheme.shapes.large
                            ) {
                                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                    recipe.ingredients.forEach { ingredient ->
                                        ListItem(
                                            headlineContent = { Text(ingredient.name) },
                                            supportingContent = {
                                                Text("${ingredient.amount} ${ingredient.unit ?: ""}".trim())
                                            }
                                        )
                                    }
                                }
                            }
                        }
                        
                        // Шаги приготовления
                        item {
                            Text(
                                text = "Шаги приготовления",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp)
                            )
                        }
                        item {
                            ElevatedCard(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(),
                                shape = MaterialTheme.shapes.large
                            ) {
                                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                    recipe.steps.forEach { step ->
                                        ListItem(
                                            headlineContent = { Text("Шаг ${step.stepNumber}") },
                                            supportingContent = { Text(step.description) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            uiState.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
        }
    }
}

