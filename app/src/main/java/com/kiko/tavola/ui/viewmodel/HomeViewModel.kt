package com.kiko.tavola.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiko.tavola.domain.model.Recipe
import com.kiko.tavola.domain.repository.CategoryRepository
import com.kiko.tavola.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        loadData()
    }
    
    fun loadData() {
        viewModelScope.launch {
            val refreshing = _uiState.value.recipes.isNotEmpty() || _uiState.value.categories.isNotEmpty()
            _uiState.update {
                it.copy(
                    isLoading = !refreshing,
                    isRefreshing = refreshing,
                    errorMessage = null
                )
            }
            
            try {
                combine(
                    recipeRepository.getAllRecipes(),
                    recipeRepository.getFeaturedRecipes(),
                    categoryRepository.getAllCategories()
                ) { recipes, featured, categories ->
                    Triple(recipes, featured, categories)
                }.collect { (recipes, featured, categories) ->
                    _uiState.update {
                        it.copy(
                            recipes = recipes,
                            featuredRecipes = featured,
                            categories = categories,
                            isLoading = false,
                            isRefreshing = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = e.message ?: "Произошла ошибка при загрузке данных"
                    )
                }
            }
        }
    }

    fun refresh() {
        // имитация "реального" рефреша: на моках просто повторно загружаем потоки
        loadData()
    }
    
    fun searchRecipes(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        
        if (query.isBlank()) {
            loadData()
            return
        }
        
        viewModelScope.launch {
            try {
                recipeRepository.searchRecipes(query).collect { recipes ->
                    _uiState.update { it.copy(recipes = recipes) }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = e.message ?: "Ошибка поиска")
                }
            }
        }
    }
}

