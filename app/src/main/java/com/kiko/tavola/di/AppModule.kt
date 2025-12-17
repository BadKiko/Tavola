package com.kiko.tavola.di

import com.kiko.tavola.data.remote.FoodApiService
import com.kiko.tavola.data.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Модуль для предоставления общих зависимостей приложения
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFoodApiService(): FoodApiService {
        return RetrofitClient.foodApiService
    }
}

