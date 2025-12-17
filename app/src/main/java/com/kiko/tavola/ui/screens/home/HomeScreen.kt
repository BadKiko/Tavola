package com.kiko.tavola.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalButton
import com.kiko.tavola.ui.components.CategoryCard
import com.kiko.tavola.ui.components.RecipeCard
import com.kiko.tavola.ui.components.SearchTopBar
import com.kiko.tavola.ui.components.SettingsBottomSheet
import com.kiko.tavola.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(
    onRecipeClick: (String) -> Unit,
    onCategoryClick: () -> Unit,
    onViewAllRecipes: () -> Unit,
    useDynamicColor: Boolean,
    onUseDynamicColorChange: (Boolean) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showSettings by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { snackbarHostState.showSnackbar(it) }
    }
    
    if (showSettings) {
        SettingsBottomSheet(
            useDynamicColor = useDynamicColor,
            onUseDynamicColorChange = onUseDynamicColorChange,
            onDismiss = { showSettings = false }
        )
    }

    Scaffold(
        topBar = {
            SearchTopBar(
                query = uiState.searchQuery,
                onQueryChange = { viewModel.searchRecipes(it) },
                onSearch = { viewModel.searchRecipes(uiState.searchQuery) },
                onSettingsClick = { showSettings = true }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                LoadingIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                PullToRefreshBox(
                    state = pullToRefreshState,
                    isRefreshing = uiState.isRefreshing,
                    onRefresh = { viewModel.refresh() },
                    modifier = Modifier.fillMaxSize(),
                    indicator = {
                        PullToRefreshDefaults.LoadingIndicator(
                            state = pullToRefreshState,
                            isRefreshing = uiState.isRefreshing,
                            modifier = Modifier.align(Alignment.TopCenter)
                        )
                    }
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        // Быстрые категории
                        if (uiState.categories.isNotEmpty()) {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Категории",
                                        style = MaterialTheme.typography.headlineSmall
                                    )
                                    FilledTonalButton(onClick = onCategoryClick) {
                                        Text("Все")
                                    }
                                }
                            }
                            item {
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    contentPadding = PaddingValues(horizontal = 4.dp)
                                ) {
                                    items(
                                        items = uiState.categories.take(5),
                                        key = { it.id }
                                    ) { category ->
                                        CategoryCard(
                                            category = category,
                                            onClick = onCategoryClick,
                                            modifier = Modifier
                                                .fillParentMaxWidth(0.75f)
                                        )
                                    }
                                }
                            }
                        }
                        
                        // Подборка для вас
                        if (uiState.featuredRecipes.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Подборка для вас",
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }
                            item {
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    contentPadding = PaddingValues(horizontal = 4.dp)
                                ) {
                                    items(
                                        items = uiState.featuredRecipes,
                                        key = { it.id }
                                    ) { recipe ->
                                        RecipeCard(
                                            recipe = recipe,
                                            onClick = { onRecipeClick(recipe.id) },
                                            modifier = Modifier
                                                .fillParentMaxWidth(0.88f)
                                        )
                                    }
                                }
                            }
                        }
                        
                        // Кнопка "Посмотреть все рецепты"
                        if (uiState.recipes.isNotEmpty()) {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Все рецепты",
                                        style = MaterialTheme.typography.headlineSmall
                                    )
                                    FilledTonalButton(onClick = onViewAllRecipes) {
                                        Text("Посмотреть все")
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            // Ошибки показываются через Snackbar
        }
    }
}

