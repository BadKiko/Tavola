package com.kiko.tavola.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kiko.tavola.ui.screens.categories.CategoriesScreen
import com.kiko.tavola.ui.screens.home.HomeScreen
import com.kiko.tavola.ui.screens.recipedetail.RecipeDetailScreen

@Composable
fun TavolaNavigation(
    navController: NavHostController,
    useDynamicColor: Boolean,
    onUseDynamicColorChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = TavolaRoutes.HOME,
        modifier = modifier
    ) {
        composable(TavolaRoutes.HOME) {
            HomeScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(TavolaRoutes.recipeDetail(recipeId))
                },
                onCategoryClick = {
                    navController.navigate(TavolaRoutes.CATEGORIES)
                },
                useDynamicColor = useDynamicColor,
                onUseDynamicColorChange = onUseDynamicColorChange
            )
        }
        
        composable(TavolaRoutes.CATEGORIES) {
            CategoriesScreen(
                onCategoryClick = { categoryId ->
                    // TODO: Навигация к рецептам категории
                },
                useDynamicColor = useDynamicColor,
                onUseDynamicColorChange = onUseDynamicColorChange
            )
        }
        
        composable(
            route = TavolaRoutes.RECIPE_DETAIL,
            arguments = listOf(
                navArgument("recipeId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId") ?: ""
            RecipeDetailScreen(
                recipeId = recipeId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

