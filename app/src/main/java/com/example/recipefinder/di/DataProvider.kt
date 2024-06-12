package com.example.recipefinder.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.recipefinder.data.source.DataStoreManager
import com.example.recipefinder.data.source.RecipeRepository
import com.example.recipefinder.data.source.RecipeRepositoryImplementation
import com.example.recipefinder.data.source.dataStore
import com.example.recipefinder.data.source.local.RecipeDao
import com.example.recipefinder.data.source.local.RecipeDatabase
import com.example.recipefinder.data.source.network.ApiKeyInterceptor
import com.example.recipefinder.data.source.network.NetworkDataSource
import com.example.recipefinder.data.source.network.RecipeApiService
import com.example.recipefinder.data.source.network.RecipeNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindRecipeRepository(
        repositoryImpl: RecipeRepositoryImplementation
    ): RecipeRepository
}


@Module
@InstallIn(SingletonComponent::class)
object DatabasesModule {

    @Provides
    @Singleton
    fun provideRecipeDao(database: RecipeDatabase): RecipeDao {
        return database.recipeDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ):RecipeDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            RecipeDatabase::class.java,
            "recipe_database"

        ).fallbackToDestructiveMigration().build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
class DataProviderModule {
    @Provides
    @Singleton
    fun provideNetworkDataSource(recipeApiService: RecipeApiService): NetworkDataSource {
        return RecipeNetworkDataSource(recipeApiService)
    }

    @Provides
    @Singleton
    fun provideRecipeApiService(retrofit: retrofit2.Retrofit): RecipeApiService {
        return retrofit.create(RecipeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: okhttp3.OkHttpClient): retrofit2.Retrofit {
        return retrofit2.Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .client(okHttpClient)
            .build()


    }
    @Provides
    @Singleton
    fun okHttp(
        @ApplicationContext context: Context,
        apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(ChuckerInterceptor(context))
            .build()
    }
    @Provides
    @Singleton
    fun apiKeyInterceptor(): ApiKeyInterceptor {
        val apiKey = "250220c473824b2ba3ba6c38a41e0b0d"
        return ApiKeyInterceptor(apiKey)
    }


}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context.dataStore)
    }
}