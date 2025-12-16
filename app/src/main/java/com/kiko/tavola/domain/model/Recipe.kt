package com.kiko.tavola.domain.model

/**
 * Модель рецепта
 */
data class Recipe(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val imageUrls: List<String> = listOf(imageUrl),
    val cookingTime: Int, // в минутах
    val difficulty: Difficulty,
    val servings: Int,
    val ingredients: List<Ingredient>,
    val steps: List<CookingStep>,
    val categoryId: String,
    val categoryName: String
)

enum class Difficulty {
    EASY,
    MEDIUM,
    HARD
}

data class Ingredient(
    val name: String,
    val amount: String,
    val unit: String? = null
)

data class CookingStep(
    val stepNumber: Int,
    val description: String,
    val imageUrl: String? = null
)

