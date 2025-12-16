package com.kiko.tavola.ui.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.themeDataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_settings")

@Singleton
class ThemeSettings @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val USE_DYNAMIC_COLOR_KEY = booleanPreferencesKey("use_dynamic_color")
    }
    
    val useDynamicColor: Flow<Boolean> = context.themeDataStore.data.map { preferences ->
        preferences[USE_DYNAMIC_COLOR_KEY] ?: true // По умолчанию включено
    }
    
    suspend fun setUseDynamicColor(use: Boolean) {
        context.themeDataStore.edit { preferences ->
            preferences[USE_DYNAMIC_COLOR_KEY] = use
        }
    }
}

