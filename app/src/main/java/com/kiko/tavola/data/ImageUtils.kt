package com.kiko.tavola.data

import android.util.Base64

/**
 * Утилиты для работы с изображениями из API Food.ru
 */
object ImageUtils {

    private const val CDN_BASE_URL = "https://cdn.food.ru/unsigned/fit/1366/1002/ce/0/"

    /**
     * Конвертирует image_path из JSON API в полный CDN URL
     *
     * @param imagePath путь к изображению из JSON, например "pictures/20251214/4d8Nxk.jpeg"
     * @return полный CDN URL для загрузки изображения
     */
    fun buildImageUrl(imagePath: String): String {
        // Конвертируем путь: pictures/20251214/filename.jpeg -> s3://media/pictures/2025-12-14-filename.jpeg
        val s3Path = convertToS3Path(imagePath)

        // Кодируем в base64
        val encodedPath = Base64.encodeToString(
            s3Path.toByteArray(Charsets.UTF_8),
            Base64.NO_WRAP
        )

        // Формируем полный URL
        return CDN_BASE_URL + encodedPath + ".webp"
    }

    /**
     * Конвертирует image_path в S3 путь
     *
     * @param imagePath оригинальный путь, например "pictures/20251214/4d8Nxk.jpeg"
     * @return S3 путь, например "s3://media/pictures/2025-12-14-4d8Nxk.jpeg"
     */
    private fun convertToS3Path(imagePath: String): String {
        // Разбираем путь: pictures/YYYYMMDD/filename.ext
        val parts = imagePath.split("/")
        if (parts.size < 3) return "s3://media/$imagePath"

        val dateStr = parts[1] // YYYYMMDD
        val filename = parts[2] // filename.ext

        // Конвертируем дату: 20251214 -> 2025-12-14
        val formattedDate = if (dateStr.length == 8) {
            "${dateStr.substring(0, 4)}-${dateStr.substring(4, 6)}-${dateStr.substring(6, 8)}"
        } else {
            dateStr
        }

        return "s3://media/pictures/$formattedDate-$filename"
    }

    /**
     * Проверяет, является ли строка валидным image_path из Food.ru API
     */
    fun isValidImagePath(imagePath: String): Boolean {
        return imagePath.startsWith("pictures/") && imagePath.contains("/")
    }
}

