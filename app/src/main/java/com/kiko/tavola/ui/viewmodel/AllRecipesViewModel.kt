package com.kiko.tavola.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiko.tavola.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllRecipesViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AllRecipesUiState())
    val uiState: StateFlow<AllRecipesUiState> = _uiState.asStateFlow()

    init {
        loadRecipes()
    }

    fun loadRecipes() {
        viewModelScope.launch {
            val refreshing = _uiState.value.recipes.isNotEmpty()
            _uiState.update {
                it.copy(
                    isLoading = !refreshing,
                    isRefreshing = refreshing,
                    errorMessage = null
                )
            }

            try {
                recipeRepository.getAllRecipes().collect { recipes ->
                    _uiState.update {
                        it.copy(
                            recipes = recipes,
                            isLoading = false,
                            isRefreshing = false,
                            hasMorePages = recipes.size >= 50 // Предполагаем, что если получили 50, есть еще страницы
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = e.message ?: "Произошла ошибка при загрузке рецептов"
                    )
                }
            }
        }
    }

    fun refresh() {
        loadRecipes()
    }

    fun searchRecipes(query: String) {
        _uiState.update { it.copy(searchQuery = query) }

        if (query.isBlank()) {
            loadRecipes()
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                recipeRepository.searchRecipes(query).collect { recipes ->
                    _uiState.update {
                        it.copy(
                            recipes = recipes,
                            isLoading = false,
                            hasMorePages = false // Поиск не поддерживает пагинацию
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Произошла ошибка при поиске"
                    )
                }
            }
        }
    }

    fun loadNextPage() {
        if (_uiState.value.isLoading || !_uiState.value.hasMorePages) return

        val nextPage = _uiState.value.currentPage + 1
        _uiState.update { it.copy(currentPage = nextPage) }

        // Для пагинации нужно будет обновить репозиторий
        // Пока просто перезагружаем
        loadRecipes()
    }
}

