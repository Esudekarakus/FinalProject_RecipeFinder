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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.recipefinder.NavigationActions
import com.example.recipefinder.models.RecipeModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun HomeScreen(
    viewModel: HomeVM = hiltViewModel(),
    onRecipeClick: (Int) -> Unit,
    navController: NavController
) {
    val uiState = viewModel.uiState.collectAsState().value
    val navActions = remember(navController) {
        NavigationActions(navController)
    }
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
                colors = TopAppBarDefaults.topAppBarColors(Color(0xFFFF7F50)),
                actions = {
                    IconButton(onClick = { /* Bildirimleri Aç */ }) {
                        Icon(Icons.Filled.Notifications, contentDescription = "Notifications")
                    }
                    IconButton(onClick = {
                        navActions.navigateToFavoritesScreen() // Favori ekranına yönlendirme işlevi burada çağrılır
                    }) {
                        Icon(Icons.Filled.FavoriteBorder, contentDescription = "Favorites")
                    }
                }
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(padding)
                    .padding(16.dp)
            ) {
                item {
                    GreetingSection()
                    Spacer(modifier = Modifier.height(16.dp))
                    if (uiState.randomRecipe != null) {

                        RecommendedRecipeCard(
                            recipe = uiState.randomRecipe,
                            onRecipeClick = onRecipeClick

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
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onRecipeClick(recipe.id) }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(16.dp), // Kenarları yuvarlatmak için şekil ayarlandı
            elevation = CardDefaults.elevatedCardElevation(10.dp), // Gölge efekti eklendi
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0FFE0) )
        ) {
            Column(
                modifier = Modifier.padding(16.dp) // Column içindeki metinler için padding eklendi
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
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black // Kontrast oluşturacak bir renk kullanıldı
                    ),
                    modifier = Modifier.fillMaxWidth(), // Metnin genişliğinin tamamını kaplaması için modifier eklendi
                    textAlign = TextAlign.Center // Metnin ortalanması için textAlign eklendi
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = recipe.plainSummary,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Black),
                    modifier = Modifier.padding(horizontal = 12.dp) // Text için padding eklendi
                )
                Spacer(modifier = Modifier.height(6.dp))
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
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(300.dp) // Kartın boyutunu iki katına çıkardık
            .clickable { onRecipeClick(recipe.id) },
        shape = RoundedCornerShape(16.dp), // Kenarları yuvarlatmak için şekil ayarlandı
        elevation = CardDefaults.elevatedCardElevation(8.dp), // Gölge efekti
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4)) // Sarı renk
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(recipe.image),
                contentDescription = null,
                modifier = Modifier.size(200.dp) // Resim boyutunu da kartla orantılı büyüttük
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


