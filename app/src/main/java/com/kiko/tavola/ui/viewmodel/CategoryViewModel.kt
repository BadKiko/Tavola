package com.kiko.tavola.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiko.tavola.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()
    
    init {
        loadCategories()
    }
    
    private fun loadCategories() {
        viewModelScope.launch {
            val refreshing = _uiState.value.categories.isNotEmpty()
            _uiState.update {
                it.copy(
                    isLoading = !refreshing,
                    isRefreshing = refreshing,
                    errorMessage = null
                )
            }
            
            try {
                categoryRepository.getAllCategories().collect { categories ->
                    _uiState.update {
                        it.copy(
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
                        errorMessage = e.message ?: "Произошла ошибка при загрузке категорий"
                    )
                }
            }
        }
    }

    fun refresh() {
        loadCategories()
    }
}

