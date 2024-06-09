package com.example.recipefinder.data.source.network



import com.example.recipefinder.utils.ApiResult
import com.example.recipefinder.utils.apiFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeNetworkDataSource @Inject constructor(
    private val recipeApiService: RecipeApiService
) : NetworkDataSource {

    override suspend fun getRecipes(): Flow<ApiResult<RecipeResponse>> = apiFlow {
        recipeApiService.getRecipes()
    }
}
