package com.kiko.tavola.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Bottom Navigation Bar в expressive стиле Material 3
 */
@Composable
fun TavolaBottomBar(
    navController: NavController
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Главная") },
            label = { Text("Главная") },
            selected = currentRoute == TavolaRoutes.HOME,
            onClick = {
                navController.navigate(TavolaRoutes.HOME) {
                    popUpTo(TavolaRoutes.HOME) { inclusive = false }
                }
            }
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.Category, contentDescription = "Категории") },
            label = { Text("Категории") },
            selected = currentRoute == TavolaRoutes.CATEGORIES,
            onClick = {
                navController.navigate(TavolaRoutes.CATEGORIES) {
                    popUpTo(TavolaRoutes.HOME) { inclusive = false }
                }
            }
        )
    }
}

