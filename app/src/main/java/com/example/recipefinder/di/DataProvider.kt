package com.example.recipefinder.di

import android.content.Context
import androidx.room.Room
import com.example.recipefinder.data.RecipeRepository
import com.example.recipefinder.data.RecipeRepositoryImplementation
import com.example.recipefinder.data.source.local.RecipeDao
import com.example.recipefinder.data.source.local.RecipeDatabase
import com.example.recipefinder.data.source.network.NetworkDataSource
import com.example.recipefinder.data.source.network.RecipeApiService
import com.example.recipefinder.data.source.network.RecipeNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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

        ).build()
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
            .baseUrl("https://hf-android-app.s3-eu-west-1.amazonaws.com/android-test/")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .client(okHttpClient)
            .build()


    }

}

