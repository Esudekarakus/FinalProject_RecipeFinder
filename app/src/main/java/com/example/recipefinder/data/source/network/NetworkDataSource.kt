package com.example.recipefinder.data.source.network



import com.example.recipefinder.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface NetworkDataSource {

    //random
    suspend fun getRecipes(): Flow<ApiResult<RecipeResponse>>

    suspend fun getRecipeById(id: Int): Flow<ApiResult<Recipe>>

    suspend fun getBreakfastRecipes(): Flow<ApiResult<RecipeComplexResponse>>
    suspend fun getDessertRecipes(): Flow<ApiResult<RecipeComplexResponse>>
    suspend fun getMainCourseRecipes(): Flow<ApiResult<RecipeComplexResponse>>
    suspend fun getVeganRecipes(): Flow<ApiResult<RecipeComplexResponse>>

}