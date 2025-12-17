package com.kiko.tavola.domain.model

/**
 * Модель рецепта
 */
data class Recipe(
    val id: String,
    val title: String,
    val description: String,
    val imagePath: String, // путь к изображению из API (например, "pictures/20251214/4d8Nxk.jpeg")
    val imageUrl: String = "", // полный URL, генерируется из imagePath
    val imageUrls: List<String> = listOf(imageUrl),
    val cookingTime: Int, // в минутах
    val difficulty: Difficulty,
    val servings: Int,
    val ingredients: List<Ingredient>,
    val steps: List<CookingStep>,
    val categoryId: String,
    val categoryName: String
)

enum class Difficulty {
    EASY,
    MEDIUM,
    HARD
}

data class Ingredient(
    val name: String,
    val amount: String,
    val unit: String? = null
)

data class CookingStep(
    val stepNumber: Int,
    val description: String,
    val imageUrl: String? = null
)

// API Food.ru модели
data class FoodApiResponse(
    val page: Int,
    val max_per_page: Int,
    val total_count: Int,
    val materials: List<FoodRecipe>
)

data class FoodRecipe(
    val id: Long,
    val url_part: String,
    val title: String,
    val subtitle: FoodSubtitle,
    val cover: FoodCover,
    val breadcrumbs: List<FoodBreadcrumb>,
    val author: FoodAuthor,
    val video_cover: String?,
    val difficulty_level: Int,
    val active_cooking_time: Int,
    val total_cooking_time: Int,
    val main_ingredients_block: FoodIngredientsBlock,
    val optional_ingredients_blocks: List<FoodIngredientsBlock>,
    val preparation: List<FoodStep>,
    val cooking: List<FoodStep>,
    val impression: List<FoodStep>
)

data class FoodSubtitle(
    val version: Int,
    val type: String,
    val children: List<FoodParagraph>
)

data class FoodParagraph(
    val version: Int,
    val type: String,
    val children: List<FoodText>
)

data class FoodText(
    val version: Int,
    val type: String,
    val content: String,
    val bold: Boolean,
    val italic: Boolean,
    val highlight: Boolean
)

data class FoodBreadcrumb(
    val id: Long,
    val url_part: String,
    val title: String,
    val breadcrumb_title: String?
)

data class FoodAuthor(
    val id: Long,
    val x5id: Long?,
    val first_name: String,
    val last_name: String,
    val description: String?,
    val avatar: FoodAvatar?,
    val is_blocked: Boolean,
    val is_follow: Boolean
)

data class FoodAvatar(
    val id: Long,
    val image_path: String
)

data class FoodCover(
    val image_path: String,
    val image_type: String,
    val image_size: Long,
    val image_hash: String,
    val is_marketing: Boolean,
    val author: String?,
    val source: String?,
    val text: String?,
    val caption_big: String?,
    val caption_small: String?
)

data class FoodIngredientsBlock(
    val title: String,
    val products: List<FoodProduct>
)

data class FoodProduct(
    val id: Long,
    val title: String,
    val weight: Double,
    val custom_measure: String,
    val custom_measure_count: Double
)

data class FoodStep(
    val image_path: String,
    val title: String,
    val description: FoodSubtitle
)

