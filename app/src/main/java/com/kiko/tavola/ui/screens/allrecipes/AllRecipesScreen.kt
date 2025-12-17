package com.kiko.tavola.ui.screens.allrecipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import com.kiko.tavola.ui.components.RecipeCard
import com.kiko.tavola.ui.components.SearchTopBar
import com.kiko.tavola.ui.components.SettingsBottomSheet
import com.kiko.tavola.ui.viewmodel.AllRecipesViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AllRecipesScreen(
    onRecipeClick: (String) -> Unit,
    onBack: () -> Unit,
    useDynamicColor: Boolean,
    onUseDynamicColorChange: (Boolean) -> Unit,
    viewModel: AllRecipesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val pullToRefreshState = rememberPullToRefreshState()
    var showSettings by remember { mutableStateOf(false) }

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
            LargeTopAppBar(
                title = { Text("Все рецепты") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading && uiState.recipes.isEmpty()) {
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
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Поиск в начале списка
                        item {
                            SearchTopBar(
                                query = uiState.searchQuery,
                                onQueryChange = { viewModel.searchRecipes(it) },
                                onSearch = { viewModel.searchRecipes(uiState.searchQuery) },
                                onSettingsClick = { showSettings = true }
                            )
                        }

                        // Рецепты
                        items(
                            items = uiState.recipes,
                            key = { it.id }
                        ) { recipe ->
                            RecipeCard(
                                recipe = recipe,
                                onClick = { onRecipeClick(recipe.id) },
                                modifier = Modifier.fillParentMaxWidth()
                            )
                        }

                        // Заглушка для загрузки следующей страницы
                        if (uiState.hasMorePages && !uiState.isLoading) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillParentMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Загрузить еще...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }

            uiState.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
        }
    }
}

