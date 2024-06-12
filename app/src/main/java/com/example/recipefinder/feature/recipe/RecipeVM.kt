package com.example.recipefinder.feature.recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.source.DataStoreManager
import com.example.recipefinder.data.source.RecipeRepository
import com.example.recipefinder.data.source.toEntity
import com.example.recipefinder.data.source.toRecipeModel
import com.example.recipefinder.models.RecipeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class RecipeDetailState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val recipe: RecipeModel?= null

)

@HiltViewModel
class RecipeVM
@Inject constructor(
    private val repository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeDetailState())
    val uiState: StateFlow<RecipeDetailState> = _uiState

    fun getRecipeById(recipeId: Int) {
        viewModelScope.launch {
            _uiState.value = RecipeDetailState(isLoading = true)
            repository.getRecipeById(recipeId).collect { apiResult ->
                val recipeModel = apiResult.toRecipeModel()
                if (recipeModel != null) {
                    _uiState.value = RecipeDetailState(recipe = recipeModel)
                } else {
                    _uiState.value = RecipeDetailState(isError = true)
                }
            }
        }
    }
    fun toggleFavorite(recipe: RecipeModel, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            if (!recipe.isFavorite) {
                callback(true)
                withContext(Dispatchers.IO) {
                    recipe.isFavorite = true
                }
                dataStoreManager.saveFavoriteRecipe(recipe.id)
                repository.insertRecipe(recipe.toEntity())

            } else {
                // Favori gibi davranıyoruz
                callback(false)
                withContext(Dispatchers.IO) {
                    recipe.isFavorite = false
                }
                dataStoreManager.deleteFavoriteRecipe(recipe.id)
                repository.deleteRecipe(recipe.toEntity())
            }
        }
    }

    fun checkIfFavorite(recipeId: Int, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isFavorite = dataStoreManager.isFavorite(recipeId)
            callback(isFavorite)
        }
    }
    fun updateUI() {
        _uiState.value = _uiState.value.copy()
    }
}