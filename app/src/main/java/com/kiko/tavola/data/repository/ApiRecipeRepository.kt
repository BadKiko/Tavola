package com.kiko.tavola.data.repository

import com.kiko.tavola.data.ImageUtils
import com.kiko.tavola.data.remote.FoodApiService
import com.kiko.tavola.domain.model.*
import com.kiko.tavola.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Репозиторий для работы с рецептами через API food.ru
 */
@Singleton
class ApiRecipeRepository @Inject constructor(
    private val apiService: FoodApiService
) : RecipeRepository {

    override fun getAllRecipes(): Flow<List<Recipe>> = flow {
        try {
            val response = apiService.searchRecipes(page = 1, maxPerPage = 50)
            if (response.isSuccessful) {
                val apiRecipes = response.body()?.materials?.map { it.toRecipe() } ?: emptyList()
                emit(apiRecipes)
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override suspend fun getRecipeById(id: String): Recipe? {
        // Для детального рецепта можно реализовать отдельный запрос
        // Пока возвращаем null
        return null
    }

    override fun getRecipesByCategory(categoryId: String): Flow<List<Recipe>> = flow {
        // Фильтрация по категории через API
        try {
            val response = apiService.searchRecipes(page = 1, maxPerPage = 50)
            if (response.isSuccessful) {
                val apiRecipes = response.body()?.materials
                    ?.filter { recipe ->
                        // Простая фильтрация по breadcrumbs
                        recipe.breadcrumbs.any { it.title.contains(categoryId, ignoreCase = true) }
                    }
                    ?.map { it.toRecipe() } ?: emptyList()
                emit(apiRecipes)
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override fun searchRecipes(query: String): Flow<List<Recipe>> = flow {
        // Поиск через API
        try {
            val response = apiService.searchRecipes(page = 1, maxPerPage = 50)
            if (response.isSuccessful) {
                val apiRecipes = response.body()?.materials
                    ?.filter { recipe ->
                        recipe.main_title.contains(query, ignoreCase = true) ||
                        recipe.subtitle.toPlainText().contains(query, ignoreCase = true)
                    }
                    ?.map { it.toRecipe() } ?: emptyList()
                emit(apiRecipes)
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override fun getFeaturedRecipes(): Flow<List<Recipe>> = flow {
        // Получаем первую страницу как "featured"
        try {
            val response = apiService.searchRecipes(page = 1, maxPerPage = 10)
            if (response.isSuccessful) {
                val apiRecipes = response.body()?.materials?.map { it.toRecipe() } ?: emptyList()
                emit(apiRecipes.take(4)) // Первые 4 как featured
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    /**
     * Конвертация API модели в доменную модель Recipe
     */
    private fun FoodRecipe.toRecipe(): Recipe {
        val imagePath = this.cover.image_path
        val imageUrl = ImageUtils.buildImageUrl(imagePath)

        // Собираем все изображения: обложка + изображения из шагов
        val allImagePaths = listOf(imagePath) + (this.preparation + this.cooking + this.impression).map { it.image_path }
        val allImageUrls = allImagePaths.map { ImageUtils.buildImageUrl(it) }

        return Recipe(
            id = this.id.toString(),
            title = this.title,
            description = this.subtitle.toPlainText(),
            imagePath = imagePath,
            imageUrl = imageUrl,
            imageUrls = allImageUrls,
            cookingTime = this.active_cooking_time,
            difficulty = when (this.difficulty_level) {
                1 -> Difficulty.EASY
                2 -> Difficulty.MEDIUM
                3 -> Difficulty.HARD
                else -> Difficulty.MEDIUM
            },
            servings = 4, // По умолчанию
            ingredients = this.main_ingredients_block.products.map { product ->
                Ingredient(
                    name = product.title,
                    amount = product.custom_measure_count.toString(),
                    unit = product.custom_measure
                )
            },
            steps = (this.preparation + this.cooking + this.impression).mapIndexed { index, step ->
                CookingStep(
                    stepNumber = index + 1,
                    description = step.description.toPlainText(),
                    imageUrl = ImageUtils.buildImageUrl(step.image_path)
                )
            },
            categoryId = this.breadcrumbs.firstOrNull()?.id?.toString() ?: "unknown",
            categoryName = this.breadcrumbs.firstOrNull()?.title ?: "Без категории"
        )
    }

    /**
     * Конвертация subtitle в обычный текст
     */
    private fun FoodSubtitle.toPlainText(): String {
        return this.children
            .flatMap { it.children }
            .joinToString(" ") { it.content }
            .take(200) // Ограничение длины
    }
}
