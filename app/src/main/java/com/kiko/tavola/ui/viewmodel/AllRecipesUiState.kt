package com.kiko.tavola.ui.viewmodel

import com.kiko.tavola.domain.model.Recipe

/**
 * Состояние UI для экрана всех рецептов
 */
data class AllRecipesUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val currentPage: Int = 1,
    val hasMorePages: Boolean = true,
    val searchQuery: String = "",
    val errorMessage: String? = null
)

