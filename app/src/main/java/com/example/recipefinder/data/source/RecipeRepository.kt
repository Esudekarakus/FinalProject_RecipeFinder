package com.example.recipefinder.data.source


import com.example.recipefinder.data.source.local.RecipeEntity
import com.example.recipefinder.data.source.network.Recipe
import com.example.recipefinder.data.source.network.RecipeComplexResponse
import com.example.recipefinder.data.source.network.RecipeResponse
import com.example.recipefinder.models.RecipeModel
import com.example.recipefinder.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRepices(): Flow<ApiResult<RecipeResponse>>
    suspend fun getRecipesSteam():Flow<List<RecipeEntity>>
    suspend fun getRecipeByIdSteam(recipeId:Int):Flow<RecipeModel?>
    suspend fun getRecipeById(id: Int): Flow<ApiResult<Recipe>>
    suspend fun getBreakfastRecipes(): Flow<ApiResult<RecipeComplexResponse>>
    suspend fun getDessertRecipes(): Flow<ApiResult<RecipeComplexResponse>>
    suspend fun getMainCourseRecipes(): Flow<ApiResult<RecipeComplexResponse>>
    suspend fun getVeganRecipes(): Flow<ApiResult<RecipeComplexResponse>>
    suspend fun insertRecipe(recipe: RecipeEntity)
    suspend fun deleteRecipe(recipe: RecipeEntity)


}