package com.kiko.tavola.di

import com.kiko.tavola.data.repository.ApiRecipeRepository
import com.kiko.tavola.data.repository.MockCategoryRepository
import com.kiko.tavola.domain.repository.CategoryRepository
import com.kiko.tavola.domain.repository.RecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindRecipeRepository(
        apiRecipeRepository: ApiRecipeRepository
    ): RecipeRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        mockCategoryRepository: MockCategoryRepository
    ): CategoryRepository
}

