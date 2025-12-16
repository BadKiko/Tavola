package com.kiko.tavola.domain.repository

import com.kiko.tavola.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс репозитория для работы с рецептами
 */
interface RecipeRepository {
    /**
     * Получить все рецепты
     */
    fun getAllRecipes(): Flow<List<Recipe>>
    
    /**
     * Получить рецепт по ID
     */
    suspend fun getRecipeById(id: String): Recipe?
    
    /**
     * Получить рецепты по категории
     */
    fun getRecipesByCategory(categoryId: String): Flow<List<Recipe>>
    
    /**
     * Поиск рецептов по запросу
     */
    fun searchRecipes(query: String): Flow<List<Recipe>>
    
    /**
     * Получить подборку рецептов для пользователя
     */
    fun getFeaturedRecipes(): Flow<List<Recipe>>
}

