package com.example.recipefinder.data.source.network

import com.example.recipefinder.models.RecipeModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApiService {
    @GET("recipes/random")
    suspend fun getRecipes(): Response<RecipeResponse>

    @GET("recipes/{id}/information")
    suspend fun getRecipeById(@Path("id") id: Int): Response<Recipe>

    @GET("recipes/complexSearch")
    suspend fun getRecipesComplex(): Response<RecipeResponse>

    @GET("recipes/complexSearch")
    suspend fun getBreakfastRecipes(
        @Query("type") type: String = "breakfast",

    ): Response<RecipeComplexResponse>

    @GET("recipes/complexSearch")
    suspend fun getDessertRecipes(
        @Query("type") type: String = "dessert",

    ): Response<RecipeComplexResponse>

    @GET("recipes/complexSearch")
    suspend fun getMainCourseRecipes(
        @Query("type") type: String = "main course",

    ): Response<RecipeComplexResponse>

    @GET("recipes/complexSearch")
    suspend fun getVeganRecipes(
        @Query("diet") diet: String = "vegan",

    ): Response<RecipeComplexResponse>

}
