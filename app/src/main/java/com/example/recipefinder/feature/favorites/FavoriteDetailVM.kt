package com.example.recipefinder.feature.favorites

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.source.DataStoreManager
import com.example.recipefinder.data.source.RecipeRepository
import com.example.recipefinder.data.source.toEntity
import com.example.recipefinder.models.RecipeModel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


data class FavoriteDetailState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val recipe: RecipeModel? = null
)
@HiltViewModel
class FavoriteDetailVM @Inject constructor(
    private val repository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteDetailState())
    val uiState: StateFlow<FavoriteDetailState> = _uiState

    fun getByIdSteam(recipeId: Int) {
        viewModelScope.launch {
            _uiState.value = FavoriteDetailState(isLoading = true)
            repository.getRecipeByIdSteam(recipeId).collect { recipeModel ->
                if (recipeModel != null) {
                    _uiState.value = FavoriteDetailState(recipe = recipeModel)
                } else {
                    _uiState.value = FavoriteDetailState(isError = true)
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

}