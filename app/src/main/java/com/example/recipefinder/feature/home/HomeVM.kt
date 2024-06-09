package com.example.recipefinder.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.RecipeRepository
import com.example.recipefinder.data.source.local.RecipeEntity
import com.example.recipefinder.data.source.toModel
import com.example.recipefinder.models.RecipeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeState(
    val isLoading: Boolean = false,
    val recipes: List<RecipeModel> = emptyList(),
    val isError: Boolean = false

)

@HiltViewModel
class HomeVM @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel(){
    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState

    fun getRecipes() {

        val internetOnline= true
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isLoading = true)
            }
            if(internetOnline){
                recipeRepository.getRepices().collect()
            }
            observerRecipes()
        }
    }

    private fun observerRecipes() {
        viewModelScope.launch {
            recipeRepository.getRecipesSteam().collect {
                val recipes = it.map { recipe ->
                    recipe.toModel()
                }
                _uiState.update { state ->
                    state.copy(isLoading = false, recipes = recipes)
                }
            }
        }
    }


}