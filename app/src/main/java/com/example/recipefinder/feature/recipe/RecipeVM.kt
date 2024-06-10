package com.example.recipefinder.feature.recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.RecipeRepository
import com.example.recipefinder.models.RecipeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeDetailState())
    val uiState: StateFlow<RecipeDetailState> = _uiState

    fun getRecipeById(recipeId: Int) {
        viewModelScope.launch {
            _uiState.value = RecipeDetailState(isLoading = true)
            repository.getRecipeByIdSteam(recipeId).collect { recipe ->
                if (recipe != null) {
                    _uiState.value = RecipeDetailState(recipe = recipe)
                } else {
                    _uiState.value = RecipeDetailState(isError = true)
                }
            }
        }
    }


}