package com.example.recipefinder

import androidx.navigation.NavController

object Destination {
    const val SPLASH ="splash"
    const val HOME= "home"
    const val RECIPE_DETAIL="recipe_detail/{id}"
    const val FAVORITES_SCREEN="favorites"
}

class NavigationActions(private val navController: NavController){
    fun navigateToHome(){
        navController.navigate(Destination.HOME){
            popUpTo(Destination.SPLASH){
                inclusive=true
                saveState=true
            }
        }
    }


    fun navigateToRecipeDetail(id:Int){
        navController.navigate(
            Destination.RECIPE_DETAIL.replace("{id}", id.toString()),
        ) {
            popUpTo(Destination.HOME) {
                saveState = true
            }
        }
    }
}