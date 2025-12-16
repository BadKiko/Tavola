package com.kiko.tavola.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Модуль для предоставления общих зависимостей приложения
 * В будущем здесь можно добавить предоставление OkHttp, Retrofit и других зависимостей
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Здесь можно добавить @Provides методы для общих зависимостей
    // Например, OkHttpClient, Retrofit и т.д.
}

