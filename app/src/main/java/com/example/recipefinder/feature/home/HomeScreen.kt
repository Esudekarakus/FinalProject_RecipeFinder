package com.example.recipefinder.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.recipefinder.models.RecipeModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun HomeScreen(
    viewModel: HomeVM = hiltViewModel(),
    onRecipeClick: (Int) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.getRandomRecipe()
        viewModel.getBreakfastRecipes()
        viewModel.getDessertRecipes()
        viewModel.getMainCourseRecipes()
        viewModel.getVeganRecipes()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Recipe App") },
                colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
                actions = {
                    IconButton(onClick = { /* Bildirimleri Aç */ }) {
                        Icon(Icons.Filled.Notifications, contentDescription = "Notifications")
                    }
                    IconButton(onClick = { /* Favorileri Göster */ }) {
                        Icon(Icons.Filled.FavoriteBorder, contentDescription = "Favorites")
                    }
                }
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFC0CB))
                    .padding(padding)
                    .padding(16.dp)
            ) {
                item {
                    GreetingSection()
                    Spacer(modifier = Modifier.height(16.dp))
                    if (uiState.randomRecipe != null) {

                        RecommendedRecipeCard(
                            recipe = uiState.randomRecipe,
                            onRecipeClick = onRecipeClick,
                            viewModel = viewModel
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    RecipeCategorySection("Breakfast Recipes", uiState.breakfastRecipes, onRecipeClick)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    RecipeCategorySection("Dessert Recipes", uiState.dessertRecipes, onRecipeClick)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    RecipeCategorySection("Main Course Recipes", uiState.mainCourseRecipes, onRecipeClick)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    RecipeCategorySection("Vegan Recipes", uiState.veganRecipes, onRecipeClick)
                }
            }
        }
    )
}


@Composable
fun GreetingSection() {
    Text(
        text = "Welcome to Recipe App!",
        style = MaterialTheme.typography.titleMedium
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Get ready to spice up your kitchen!",
        style = MaterialTheme.typography.titleSmall,
        color = Color.Gray
    )
}
@Composable
fun RecommendedRecipeCard(
    recipe: RecipeModel,
    onRecipeClick: (Int) -> Unit,
    viewModel: HomeVM
) {
    var isFavorite by remember { mutableStateOf(false) }

    // Favori durumunu kontrol etmek için bir işlev
    val checkAndToggleFavorite: () -> Unit = {
        viewModel.checkIfFavorite(recipe.id) { favorite ->
            isFavorite = favorite
        }
        viewModel.toggleFavorite(recipe) { newFavorite ->
            isFavorite = newFavorite
        }
    }

    LaunchedEffect(Unit) {
        checkAndToggleFavorite() // Başlangıçta favori durumunu kontrol et ve güncelle
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onRecipeClick(recipe.id) }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Image(
                    painter = rememberImagePainter(recipe.image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = recipe.plainSummary,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Kalbe tıklama işlevi buraya taşındı
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Red else Color.Gray, // Favori ise kırmızı, değilse gri
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            checkAndToggleFavorite() // Kalbe tıkladığında favori durumu kontrol et ve güncelle
                        }
                )
            }
        }
    }
}







@Composable
fun RecipeCategorySection(
    categoryName: String,
    recipes: List<RecipeModel>,
    onRecipeClick: (Int) -> Unit
) {
    Column {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            items(recipes) { recipe ->
                RecipeListItem(recipe, onRecipeClick)
            }
        }
    }
}

@Composable
fun RecipeListItem(recipe: RecipeModel, onRecipeClick: (Int) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp)
            .clickable { onRecipeClick(recipe.id) }
    ) {
        Image(
            painter = rememberImagePainter(recipe.image),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = recipe.title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        //Icon(Icons.Filled.Favorite, contentDescription = "Favorite", tint = Color.Red, modifier = Modifier.size(24.dp))
    }
}
