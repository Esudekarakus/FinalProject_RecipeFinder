package com.example.recipefinder.feature.splashScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.recipefinder.ui.theme.Purple40
import kotlinx.coroutines.delay



import kotlinx.coroutines.delay
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
    onSplashFinished: (Boolean) -> Unit,
) {

    LaunchedEffect(Unit) {
        delay(2000)
        onSplashFinished(true)
    }

    // center of text
    Scaffold(
        containerColor = Purple40,
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Text(
                text = "Splash Screen",
                style = TextStyle(
                    color = Color.White
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}