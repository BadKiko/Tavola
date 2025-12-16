package com.kiko.tavola.data.repository

import com.kiko.tavola.domain.model.CookingStep
import com.kiko.tavola.domain.model.Difficulty
import com.kiko.tavola.domain.model.Ingredient
import com.kiko.tavola.domain.model.Recipe
import com.kiko.tavola.domain.repository.RecipeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Моковая реализация репозитория рецептов
 */
@Singleton
class MockRecipeRepository @Inject constructor() : RecipeRepository {
    
    private val mockRecipes = listOf(
        Recipe(
            id = "1",
            title = "Паста Карбонара",
            description = "Классическая итальянская паста с беконом, яйцами и сыром пармезан. Невероятно вкусное и сытное блюдо.",
            imageUrl = "https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?w=800",
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?w=1200",
                "https://images.unsplash.com/photo-1521389508051-d7ffb5dc8dca?w=1200"
            ),
            cookingTime = 20,
            difficulty = Difficulty.MEDIUM,
            servings = 4,
            ingredients = listOf(
                Ingredient("Спагетти", "400", "г"),
                Ingredient("Бекон", "200", "г"),
                Ingredient("Яйца", "4", "шт"),
                Ingredient("Пармезан", "100", "г"),
                Ingredient("Черный перец", "по вкусу", null),
                Ingredient("Соль", "по вкусу", null)
            ),
            steps = listOf(
                CookingStep(1, "Вскипятите воду в большой кастрюле, добавьте соль и отварите спагетти согласно инструкции на упаковке.", null),
                CookingStep(2, "Пока варится паста, нарежьте бекон мелкими кусочками и обжарьте на сковороде до хрустящей корочки.", null),
                CookingStep(3, "В отдельной миске взбейте яйца с тертым пармезаном и черным перцем.", null),
                CookingStep(4, "Откиньте спагетти на дуршлаг, сохранив немного воды для пасты. Добавьте пасту в сковороду с беконом.", null),
                CookingStep(5, "Снимите сковороду с огня, добавьте яичную смесь и быстро перемешайте. Добавьте немного воды для пасты, если нужно.", null),
                CookingStep(6, "Подавайте горячим, посыпав дополнительным пармезаном и перцем.", null)
            ),
            categoryId = "1",
            categoryName = "Итальянская кухня"
        ),
        Recipe(
            id = "2",
            title = "Салат Цезарь",
            description = "Знаменитый салат с курицей, салатом романо, сухариками и соусом цезарь. Классика ресторанной кухни.",
            imageUrl = "https://images.unsplash.com/photo-1546793665-c74683f339c1?w=800",
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1546793665-c74683f339c1?w=1200",
                "https://images.unsplash.com/photo-1551892374-ecf8754cf8b0?w=1200",
                "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=1200"
            ),
            cookingTime = 25,
            difficulty = Difficulty.EASY,
            servings = 2,
            ingredients = listOf(
                Ingredient("Куриная грудка", "300", "г"),
                Ingredient("Салат романо", "1", "пучок"),
                Ingredient("Сухарики", "100", "г"),
                Ingredient("Пармезан", "50", "г"),
                Ingredient("Чеснок", "2", "зубчика"),
                Ingredient("Анчоусы", "3", "шт"),
                Ingredient("Оливковое масло", "4", "ст.л."),
                Ingredient("Лимонный сок", "2", "ст.л.")
            ),
            steps = listOf(
                CookingStep(1, "Обжарьте куриную грудку на сковороде до готовности, нарежьте полосками.", null),
                CookingStep(2, "Промойте и высушите салат романо, разорвите на крупные куски.", null),
                CookingStep(3, "Приготовьте соус цезарь: измельчите чеснок и анчоусы, смешайте с оливковым маслом и лимонным соком.", null),
                CookingStep(4, "В большой миске смешайте салат с соусом, добавьте сухарики и тертый пармезан.", null),
                CookingStep(5, "Выложите курицу сверху и подавайте сразу.", null)
            ),
            categoryId = "2",
            categoryName = "Салаты"
        ),
        Recipe(
            id = "3",
            title = "Тирамису",
            description = "Нежный итальянский десерт с кофе, маскарпоне и какао. Идеальное завершение любого ужина.",
            imageUrl = "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=800",
            cookingTime = 30,
            difficulty = Difficulty.MEDIUM,
            servings = 6,
            ingredients = listOf(
                Ingredient("Маскарпоне", "500", "г"),
                Ingredient("Яйца", "4", "шт"),
                Ingredient("Сахар", "100", "г"),
                Ingredient("Печенье савоярди", "200", "г"),
                Ingredient("Эспрессо", "200", "мл"),
                Ingredient("Какао-порошок", "2", "ст.л."),
                Ingredient("Ликер Амаретто", "2", "ст.л.")
            ),
            steps = listOf(
                CookingStep(1, "Приготовьте крепкий эспрессо и дайте остыть. Добавьте ликер.", null),
                CookingStep(2, "Отделите желтки от белков. Взбейте желтки с сахаром до пышной массы.", null),
                CookingStep(3, "Добавьте маскарпоне к желткам и аккуратно перемешайте.", null),
                CookingStep(4, "Взбейте белки до пиков и аккуратно введите в крем.", null),
                CookingStep(5, "Быстро обмакните печенье в кофе и выложите в форму.", null),
                CookingStep(6, "Выложите слой крема, затем еще один слой печенья и крема.", null),
                CookingStep(7, "Охладите в холодильнике минимум 4 часа. Перед подачей посыпьте какао.", null)
            ),
            categoryId = "3",
            categoryName = "Десерты"
        ),
        Recipe(
            id = "4",
            title = "Борщ украинский",
            description = "Классический украинский борщ с говядиной, свеклой и капустой. Национальное блюдо с богатой историей.",
            imageUrl = "https://images.unsplash.com/photo-1572441713132-51c75654db73?w=800",
            cookingTime = 120,
            difficulty = Difficulty.MEDIUM,
            servings = 6,
            ingredients = listOf(
                Ingredient("Говядина", "500", "г"),
                Ingredient("Свекла", "2", "шт"),
                Ingredient("Капуста белокочанная", "300", "г"),
                Ingredient("Морковь", "1", "шт"),
                Ingredient("Лук репчатый", "1", "шт"),
                Ingredient("Картофель", "2", "шт"),
                Ingredient("Томатная паста", "2", "ст.л."),
                Ingredient("Уксус", "1", "ст.л."),
                Ingredient("Чеснок", "3", "зубчика"),
                Ingredient("Сметана", "для подачи", null)
            ),
            steps = listOf(
                CookingStep(1, "Сварите мясной бульон: залейте мясо холодной водой, доведите до кипения, снимите пену и варите 1.5 часа.", null),
                CookingStep(2, "Натрите свеклу на крупной терке, обжарьте с уксусом и томатной пастой.", null),
                CookingStep(3, "Нарежьте капусту, морковь, лук. Картофель нарежьте кубиками.", null),
                CookingStep(4, "Достаньте мясо из бульона, нарежьте и верните обратно.", null),
                CookingStep(5, "Добавьте в бульон капусту и картофель, варите 15 минут.", null),
                CookingStep(6, "Добавьте морковь, лук и свеклу, варите еще 10 минут.", null),
                CookingStep(7, "Добавьте измельченный чеснок, посолите, поперчите. Варите еще 5 минут.", null),
                CookingStep(8, "Подавайте горячим со сметаной и свежей зеленью.", null)
            ),
            categoryId = "4",
            categoryName = "Первые блюда"
        ),
        Recipe(
            id = "5",
            title = "Стейк из говядины",
            description = "Сочный стейк средней прожарки с ароматными травами. Ресторанное качество в домашних условиях.",
            imageUrl = "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=800",
            cookingTime = 20,
            difficulty = Difficulty.MEDIUM,
            servings = 2,
            ingredients = listOf(
                Ingredient("Говяжья вырезка", "500", "г"),
                Ingredient("Оливковое масло", "2", "ст.л."),
                Ingredient("Чеснок", "4", "зубчика"),
                Ingredient("Розмарин", "2", "веточки"),
                Ingredient("Тимьян", "2", "веточки"),
                Ingredient("Сливочное масло", "50", "г"),
                Ingredient("Соль", "по вкусу", null),
                Ingredient("Черный перец", "по вкусу", null)
            ),
            steps = listOf(
                CookingStep(1, "Достаньте мясо из холодильника за 30 минут до готовки, чтобы оно достигло комнатной температуры.", null),
                CookingStep(2, "Хорошо посолите и поперчите стейк со всех сторон.", null),
                CookingStep(3, "Разогрейте сковороду на сильном огне, добавьте оливковое масло.", null),
                CookingStep(4, "Обжарьте стейк по 3-4 минуты с каждой стороны для средней прожарки.", null),
                CookingStep(5, "Добавьте сливочное масло, чеснок и травы. Наклоните сковороду и поливайте стейк маслом 2 минуты.", null),
                CookingStep(6, "Достаньте стейк, дайте отдохнуть 5 минут перед нарезкой.", null),
                CookingStep(7, "Нарежьте поперек волокон и подавайте горячим.", null)
            ),
            categoryId = "5",
            categoryName = "Мясо"
        ),
        Recipe(
            id = "6",
            title = "Суши роллы Калифорния",
            description = "Популярные роллы с крабом, авокадо и огурцом. Идеальный выбор для любителей японской кухни.",
            imageUrl = "https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?w=800",
            cookingTime = 45,
            difficulty = Difficulty.HARD,
            servings = 4,
            ingredients = listOf(
                Ingredient("Рис для суши", "300", "г"),
                Ingredient("Нори", "4", "листа"),
                Ingredient("Крабовое мясо", "200", "г"),
                Ingredient("Авокадо", "1", "шт"),
                Ingredient("Огурец", "1", "шт"),
                Ingredient("Рисовый уксус", "3", "ст.л."),
                Ingredient("Сахар", "1", "ст.л."),
                Ingredient("Соль", "0.5", "ч.л."),
                Ingredient("Васаби", "по вкусу", null),
                Ingredient("Имбирь маринованный", "для подачи", null),
                Ingredient("Соевый соус", "для подачи", null)
            ),
            steps = listOf(
                CookingStep(1, "Отварите рис для суши согласно инструкции. Смешайте уксус, сахар и соль, добавьте в горячий рис.", null),
                CookingStep(2, "Охладите рис до комнатной температуры.", null),
                CookingStep(3, "Нарежьте авокадо и огурец тонкими полосками.", null),
                CookingStep(4, "Выложите лист нори на бамбуковый коврик блестящей стороной вниз.", null),
                CookingStep(5, "Равномерно распределите рис по нори, оставив верхний край свободным.", null),
                CookingStep(6, "Переверните нори рисом вниз, выложите крабовое мясо, авокадо и огурец.", null),
                CookingStep(7, "Аккуратно сверните ролл с помощью коврика.", null),
                CookingStep(8, "Нарежьте ролл на 8 кусочков острым ножом, смачивая его водой.", null),
                CookingStep(9, "Подавайте с васаби, имбирем и соевым соусом.", null)
            ),
            categoryId = "6",
            categoryName = "Японская кухня"
        ),
        Recipe(
            id = "7",
            title = "Омлет с грибами и сыром",
            description = "Сытный и питательный завтрак с грибами, сыром и зеленью. Начинайте день с пользой!",
            imageUrl = "https://images.unsplash.com/photo-1615367423057-4d4c6ba49e0a?w=800",
            cookingTime = 15,
            difficulty = Difficulty.EASY,
            servings = 2,
            ingredients = listOf(
                Ingredient("Яйца", "4", "шт"),
                Ingredient("Шампиньоны", "200", "г"),
                Ingredient("Сыр твердый", "100", "г"),
                Ingredient("Молоко", "2", "ст.л."),
                Ingredient("Лук репчатый", "0.5", "шт"),
                Ingredient("Зелень", "по вкусу", null),
                Ingredient("Соль", "по вкусу", null),
                Ingredient("Перец", "по вкусу", null),
                Ingredient("Масло растительное", "2", "ст.л.")
            ),
            steps = listOf(
                CookingStep(1, "Нарежьте грибы и лук, обжарьте на сковороде до золотистого цвета.", null),
                CookingStep(2, "Взбейте яйца с молоком, солью и перцем.", null),
                CookingStep(3, "Вылейте яичную смесь на грибы, уменьшите огонь.", null),
                CookingStep(4, "Когда низ схватится, посыпьте тертым сыром и зеленью.", null),
                CookingStep(5, "Сложите омлет пополам и доведите до готовности.", null),
                CookingStep(6, "Подавайте горячим с хлебом.", null)
            ),
            categoryId = "7",
            categoryName = "Завтраки"
        ),
        Recipe(
            id = "8",
            title = "Пад Тай",
            description = "Традиционная тайская лапша с креветками, тофу и кисло-сладким соусом. Взрыв вкусов!",
            imageUrl = "https://images.unsplash.com/photo-1559314809-0d155014e29e?w=800",
            cookingTime = 25,
            difficulty = Difficulty.MEDIUM,
            servings = 2,
            ingredients = listOf(
                Ingredient("Рисовая лапша", "200", "г"),
                Ingredient("Креветки", "200", "г"),
                Ingredient("Тофу", "150", "г"),
                Ingredient("Яйца", "2", "шт"),
                Ingredient("Ростки фасоли", "100", "г"),
                Ingredient("Лук-шалот", "2", "шт"),
                Ingredient("Чеснок", "3", "зубчика"),
                Ingredient("Рыбный соус", "3", "ст.л."),
                Ingredient("Сок лайма", "2", "ст.л."),
                Ingredient("Коричневый сахар", "2", "ст.л."),
                Ingredient("Арахис", "50", "г"),
                Ingredient("Лайм", "для подачи", null)
            ),
            steps = listOf(
                CookingStep(1, "Замочите рисовую лапшу в теплой воде на 20 минут.", null),
                CookingStep(2, "Приготовьте соус: смешайте рыбный соус, сок лайма и сахар.", null),
                CookingStep(3, "Обжарьте тофу до золотистой корочки, нарежьте кубиками.", null),
                CookingStep(4, "Обжарьте креветки на сильном огне, добавьте яйца и перемешайте.", null),
                CookingStep(5, "Добавьте чеснок, лук-шалот и лапшу, готовьте 2 минуты.", null),
                CookingStep(6, "Добавьте соус и готовьте еще 2 минуты, помешивая.", null),
                CookingStep(7, "Добавьте ростки фасоли и арахис, перемешайте.", null),
                CookingStep(8, "Подавайте с дольками лайма и дополнительным арахисом.", null)
            ),
            categoryId = "6",
            categoryName = "Азиатская кухня"
        ),
        Recipe(
            id = "9",
            title = "Чизкейк Нью-Йорк",
            description = "Классический американский чизкейк с нежным творожным вкусом. Десерт для особых случаев.",
            imageUrl = "https://images.unsplash.com/photo-1524351199678-941a58a3df50?w=800",
            cookingTime = 90,
            difficulty = Difficulty.MEDIUM,
            servings = 8,
            ingredients = listOf(
                Ingredient("Творожный сыр", "900", "г"),
                Ingredient("Сахар", "200", "г"),
                Ingredient("Яйца", "4", "шт"),
                Ingredient("Сливки 33%", "200", "мл"),
                Ingredient("Ванильный экстракт", "1", "ч.л."),
                Ingredient("Печенье песочное", "200", "г"),
                Ingredient("Сливочное масло", "100", "г"),
                Ingredient("Крахмал", "2", "ст.л.")
            ),
            steps = listOf(
                CookingStep(1, "Измельчите печенье в крошку, смешайте с растопленным маслом, выложите в форму.", null),
                CookingStep(2, "Уплотните основу и уберите в холодильник на 30 минут.", null),
                CookingStep(3, "Взбейте творожный сыр с сахаром до гладкости.", null),
                CookingStep(4, "Добавьте яйца по одному, затем сливки, ваниль и крахмал.", null),
                CookingStep(5, "Вылейте начинку на основу, разровняйте.", null),
                CookingStep(6, "Выпекайте при 160°C в течение 60-70 минут, пока края не схватятся, а середина слегка покачивается.", null),
                CookingStep(7, "Остудите в выключенной духовке с приоткрытой дверцей 1 час.", null),
                CookingStep(8, "Охладите в холодильнике минимум 4 часа перед подачей.", null)
            ),
            categoryId = "3",
            categoryName = "Десерты"
        ),
        Recipe(
            id = "10",
            title = "Рататуй",
            description = "Французское овощное рагу из баклажанов, кабачков и помидоров. Здоровое и вкусное блюдо.",
            imageUrl = "https://images.unsplash.com/photo-1572441713132-51c75654db73?w=800",
            cookingTime = 60,
            difficulty = Difficulty.EASY,
            servings = 4,
            ingredients = listOf(
                Ingredient("Баклажаны", "2", "шт"),
                Ingredient("Кабачки", "2", "шт"),
                Ingredient("Помидоры", "4", "шт"),
                Ingredient("Перец болгарский", "2", "шт"),
                Ingredient("Лук репчатый", "1", "шт"),
                Ingredient("Чеснок", "4", "зубчика"),
                Ingredient("Оливковое масло", "4", "ст.л."),
                Ingredient("Базилик", "по вкусу", null),
                Ingredient("Тимьян", "по вкусу", null),
                Ingredient("Соль", "по вкусу", null),
                Ingredient("Перец", "по вкусу", null)
            ),
            steps = listOf(
                CookingStep(1, "Нарежьте все овощи кружками одинаковой толщины.", null),
                CookingStep(2, "Обжарьте лук и чеснок до прозрачности.", null),
                CookingStep(3, "Выложите помидоры в форму, посолите и поперчите.", null),
                CookingStep(4, "Выложите овощи по кругу, чередуя баклажаны, кабачки и перец.", null),
                CookingStep(5, "Полейте оливковым маслом, посыпьте травами.", null),
                CookingStep(6, "Накройте фольгой и запекайте при 180°C 40 минут.", null),
                CookingStep(7, "Снимите фольгу и запекайте еще 20 минут до золотистого цвета.", null),
                CookingStep(8, "Подавайте горячим или холодным с хлебом.", null)
            ),
            categoryId = "8",
            categoryName = "Овощные блюда"
        )
    )
    
    override fun getAllRecipes(): Flow<List<Recipe>> = flow {
        delay(500) // Имитация задержки сети
        emit(mockRecipes)
    }
    
    override suspend fun getRecipeById(id: String): Recipe? {
        delay(300)
        return mockRecipes.find { it.id == id }
    }
    
    override fun getRecipesByCategory(categoryId: String): Flow<List<Recipe>> = flow {
        delay(400)
        emit(mockRecipes.filter { it.categoryId == categoryId })
    }
    
    override fun searchRecipes(query: String): Flow<List<Recipe>> = flow {
        delay(300)
        val lowercaseQuery = query.lowercase()
        val filtered = mockRecipes.filter {
            it.title.lowercase().contains(lowercaseQuery) ||
            it.description.lowercase().contains(lowercaseQuery) ||
            it.ingredients.any { ingredient -> ingredient.name.lowercase().contains(lowercaseQuery) }
        }
        emit(filtered)
    }
    
    override fun getFeaturedRecipes(): Flow<List<Recipe>> = flow {
        delay(400)
        // Подборка для пользователя - первые 4 рецепта
        emit(mockRecipes.take(4))
    }
}

