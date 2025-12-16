package com.kiko.tavola.data.repository

import com.kiko.tavola.domain.model.Category
import com.kiko.tavola.domain.repository.CategoryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Моковая реализация репозитория категорий
 */
@Singleton
class MockCategoryRepository @Inject constructor() : CategoryRepository {
    
    private val mockCategories = listOf(
        Category(
            id = "1",
            name = "Итальянская кухня",
            imageUrl = "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=600",
            description = "Паста, пицца, ризотто и другие классические итальянские блюда"
        ),
        Category(
            id = "2",
            name = "Салаты",
            imageUrl = "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=600",
            description = "Свежие и полезные салаты для здорового питания"
        ),
        Category(
            id = "3",
            name = "Десерты",
            imageUrl = "https://images.unsplash.com/photo-1551024506-0bccd828d307?w=600",
            description = "Сладкие угощения для завершения трапезы"
        ),
        Category(
            id = "4",
            name = "Первые блюда",
            imageUrl = "https://images.unsplash.com/photo-1547592166-23ac45744acd?w=600",
            description = "Супы и борщи для согревающего обеда"
        ),
        Category(
            id = "5",
            name = "Мясо",
            imageUrl = "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=600",
            description = "Стейки, жаркое и другие мясные блюда"
        ),
        Category(
            id = "6",
            name = "Азиатская кухня",
            imageUrl = "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=600",
            description = "Японская, тайская и другие азиатские блюда"
        ),
        Category(
            id = "7",
            name = "Завтраки",
            imageUrl = "https://images.unsplash.com/photo-1525351484163-7529414344d8?w=600",
            description = "Идеальное начало дня"
        ),
        Category(
            id = "8",
            name = "Овощные блюда",
            imageUrl = "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=600",
            description = "Полезные и вкусные овощные блюда"
        )
    )
    
    override fun getAllCategories(): Flow<List<Category>> = flow {
        delay(300) // Имитация задержки сети
        emit(mockCategories)
    }
    
    override suspend fun getCategoryById(id: String): Category? {
        delay(200)
        return mockCategories.find { it.id == id }
    }
}

