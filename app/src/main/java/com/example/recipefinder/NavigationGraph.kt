package com.example.recipefinder

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.recipefinder.feature.favorites.FavoriteDetailScreen
import com.example.recipefinder.feature.favorites.FavoriteScreen
import com.example.recipefinder.feature.home.HomeScreen
import com.example.recipefinder.feature.recipe.RecipeScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun RecipeAppNavigationGraph(
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destination.HOME,
    navActions: NavigationActions = remember(navController) {
        NavigationActions(navController)
    },
    onService: (Boolean) -> Unit,
) {
    val currentNavigationBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavigationBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = Destination.HOME
        ) {
            HomeScreen(
                onRecipeClick = { recipeId: Int -> navActions.navigateToRecipeDetail(recipeId) },
                navController = navController
            )
        }

        composable(
            route = Destination.FAVORITES_SCREEN
        ) {
            FavoriteScreen(
                navController = navController,
                onRecipeClick = { recipeId: Int ->
                    navActions.navigateToFavoriteDetail(recipeId)
                }
            )
        }

        composable(
            route = Destination.RECIPE_DETAIL
        ) { arguments ->
            val recipeId = arguments.arguments?.getString("id") ?: "0"
            RecipeScreen(recipeId = recipeId.toInt(), navController = navController)
        }

        composable(
            route = Destination.FAVORITES_DETAIL
        ) { arguments ->
            val recipeId = arguments.arguments?.getString("id") ?: "0"
            FavoriteDetailScreen(recipeId = recipeId.toInt(), navController = navController)
        }
    }
}
