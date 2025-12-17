package com.kiko.tavola.data.remote

import com.kiko.tavola.domain.model.FoodApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API сервис для food.ru
 */
interface FoodApiService {

    @GET("datamart/v2/search")
    suspend fun searchRecipes(
        @Query("page") page: Int = 1,
        @Query("max_per_page") maxPerPage: Int = 20,
        @Query("material") material: String = "recipe",
        @Query("format") format: String = "json"
    ): Response<FoodApiResponse>

    @GET("datamart/recipes/{id}")
    suspend fun getRecipeById(
        @retrofit2.http.Path("id") id: Long,
        @Query("preview") preview: Boolean = false,
        @Query("format") format: String = "json"
    ): Response<FoodRecipe>
}
