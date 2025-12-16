package com.kiko.tavola.ui.viewmodel

import com.kiko.tavola.domain.model.Category
import com.kiko.tavola.domain.model.Recipe

/**
 * Состояние UI для главного экрана
 */
data class HomeUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val featuredRecipes: List<Recipe> = emptyList(),
    val categories: List<Category> = emptyList(),
    val searchQuery: String = "",
    val errorMessage: String? = null
)

