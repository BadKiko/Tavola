package com.kiko.tavola.ui.viewmodel

import com.kiko.tavola.domain.model.Category

/**
 * Состояние UI для экрана категорий
 */
data class CategoriesUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val categories: List<Category> = emptyList(),
    val errorMessage: String? = null
)

