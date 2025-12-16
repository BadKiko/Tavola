package com.kiko.tavola.domain.repository

import com.kiko.tavola.domain.model.Category
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс репозитория для работы с категориями
 */
interface CategoryRepository {
    /**
     * Получить все категории
     */
    fun getAllCategories(): Flow<List<Category>>
    
    /**
     * Получить категорию по ID
     */
    suspend fun getCategoryById(id: String): Category?
}

