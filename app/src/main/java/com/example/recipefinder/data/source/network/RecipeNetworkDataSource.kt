package com.example.recipefinder.data.source.network



import com.example.recipefinder.utils.ApiResult
import com.example.recipefinder.utils.apiFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeNetworkDataSource @Inject constructor(
    private val recipeApiService: RecipeApiService
) : NetworkDataSource {

    override suspend fun getRecipes(): Flow<ApiResult<RecipeResponse>> = apiFlow {
        val response= recipeApiService.getRecipes()
        println("API response: $response")
        response
    }
    override suspend fun getRecipeById(id: Int) = apiFlow {
        recipeApiService.getRecipeById(id)
    }

    override suspend fun getBreakfastRecipes(): Flow<ApiResult<RecipeComplexResponse>> = apiFlow {
        recipeApiService.getBreakfastRecipes()
    }

    override suspend fun getDessertRecipes(): Flow<ApiResult<RecipeComplexResponse>> = apiFlow {
        recipeApiService.getDessertRecipes()
    }

    override suspend fun getMainCourseRecipes(): Flow<ApiResult<RecipeComplexResponse>> = apiFlow {
        recipeApiService.getMainCourseRecipes()
    }

    override suspend fun getVeganRecipes(): Flow<ApiResult<RecipeComplexResponse>> = apiFlow {
        recipeApiService.getVeganRecipes()
    }


}
