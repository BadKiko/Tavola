package com.kiko.tavola.domain.model

/**
 * Модель категории блюд
 */
data class Category(
    val id: String,
    val name: String,
    val imageUrl: String,
    val description: String? = null
)

