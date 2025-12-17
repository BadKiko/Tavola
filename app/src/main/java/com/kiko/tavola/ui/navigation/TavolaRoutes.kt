package com.kiko.tavola.ui.navigation

/**
 * Маршруты навигации приложения
 */
object TavolaRoutes {
    const val HOME = "home"
    const val CATEGORIES = "categories"
    const val ALL_RECIPES = "all_recipes"
    const val RECIPE_DETAIL = "recipe_detail/{recipeId}"

    fun recipeDetail(recipeId: String) = "recipe_detail/$recipeId"
}

