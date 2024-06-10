package com.example.recipefinder.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.recipefinder.models.RecipeModel
import com.example.recipefinder.ui.theme.PurpleGrey40

@Composable
fun HomeScreen(
    viewModel: HomeVM = hiltViewModel(),
    onService : () -> Unit,
    onRecipeClick : (Int) -> Unit

){
    val uiState = viewModel.uiState.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.getRecipes()
    }


    Box(modifier = Modifier.fillMaxSize().background(color = PurpleGrey40)) {
        when {
            uiState.isLoading -> {

            }
            uiState.isError -> {
                Text(
                    text = "An error occurred",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(uiState.recipes) { recipe ->
                        RecipeItem(recipe = recipe, onRecipeClick = onRecipeClick)
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeItem(
    recipe: RecipeModel,
    onRecipeClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onRecipeClick(recipe.id) }
    ) {
        Image(
            painter = rememberImagePainter(recipe.image),
            contentDescription = recipe.title,
            modifier = Modifier
                .size(100.dp)
                .padding(end = 16.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}