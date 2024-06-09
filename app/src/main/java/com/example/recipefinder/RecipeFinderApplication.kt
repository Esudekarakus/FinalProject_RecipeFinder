package com.example.recipefinder

import android.app.Application
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.example.recipefinder.data.source.local.RecipeDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RecipeFinderApplication: Application() {


}
