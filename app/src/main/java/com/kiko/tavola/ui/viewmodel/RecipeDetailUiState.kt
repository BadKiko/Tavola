package com.kiko.tavola.ui.viewmodel

import com.kiko.tavola.domain.model.Recipe

/**
 * Состояние UI для экрана деталей рецепта
 */
data class RecipeDetailUiState(
    val isLoading: Boolean = false,
    val recipe: Recipe? = null,
    val errorMessage: String? = null
)

