package com.kiko.tavola.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Резервная темная цветовая схема (используется, если Monet отключен)
 */
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

/**
 * Резервная светлая цветовая схема (используется, если Monet отключен)
 */
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

/**
 * Тема приложения Tavola с поддержкой Material Expressive You и Monet
 * 
 * @param darkTheme использовать темную тему
 * @param useDynamicColor использовать динамические цвета Monet (Material You). Доступно на Android 12+
 * @param content контент приложения
 */
@Composable
fun TavolaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Использовать Monet цвета, если включено и доступно (Android 12+)
        useDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }
        // Использовать резервные цветовые схемы, если Monet отключен или недоступен
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

/**
 * Устаревшая функция темы для обратной совместимости
 * @deprecated Используйте TavolaTheme вместо этого
 */
@Deprecated("Используйте TavolaTheme", ReplaceWith("TavolaTheme(darkTheme, useDynamicColor, content)"))
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    TavolaTheme(darkTheme = darkTheme, useDynamicColor = dynamicColor, content = content)
}