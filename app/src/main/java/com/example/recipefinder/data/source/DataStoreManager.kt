package com.example.recipefinder.data.source
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class DataStoreManager(private val dataStore: DataStore<Preferences>) {

    companion object {
        val FAVORITE_KEY_PREFIX = "favorite_"
    }

    suspend fun saveFavoriteRecipe(id: Int) {
        val key = booleanPreferencesKey(FAVORITE_KEY_PREFIX + id.toString())
        dataStore.edit { preferences ->
            preferences[key] = true
        }
    }

    suspend fun deleteFavoriteRecipe(id: Int) {
        val key = booleanPreferencesKey(FAVORITE_KEY_PREFIX + id.toString())
        dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    suspend fun isFavorite(id: Int): Boolean {
        val key = booleanPreferencesKey(FAVORITE_KEY_PREFIX + id.toString())
        val preferences = dataStore.data.first()
        return preferences[key] ?: false
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "favorites")