package com.example.recipeapp.di

import android.content.Context
import com.example.recipeapp.BuildConfig
import com.example.recipeapp.main.MainRepositoryImpl
import com.example.recipeapp.main.MainViewModel
import com.example.recipeapp.common.CoroutineContextProvider
import com.example.recipeapp.common.DateTimeConverters
import com.example.recipeapp.common.SharePrefHelper
import com.example.recipeapp.datasource.RecipeApiService
import com.example.recipeapp.feature.favouriterecipe.data.FavouriteRecipesRepositoryImpl
import com.example.recipeapp.feature.favouriterecipe.presentation.FavouriteRecipesViewModel
import com.example.recipeapp.feature.recipes.data.RecipeTransformer
import com.example.recipeapp.feature.recipes.data.RecipeTransformerImpl
import com.example.recipeapp.feature.recipes.data.RecipesRepositoryImpl
import com.example.recipeapp.feature.recipes.presentation.RecipesViewModel
import com.example.recipeapp.notifications.AlarmManagerHelper
import com.example.recipeapp.notifications.NotificationHelper
import com.example.recipeapp.room.RecipeDatabase
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.DateTime
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val appModule = module {

    single {
        androidContext().getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)
    }
    single { CoroutineContextProvider() }
    single<RecipeTransformer> { RecipeTransformerImpl() }
    single { SharePrefHelper(get()) }
    single { RecipeDatabase.getInstance(androidContext()) }
    single { NotificationHelper(get()) }
    single { AlarmManagerHelper(get()) }
}

val viewModelModule = module {
    viewModel { RecipesViewModel(get(), get()) }
    viewModel { FavouriteRecipesViewModel(get()) }
    viewModel { MainViewModel(get()) }
}

val repositoryModule = module {
    single { RecipesRepositoryImpl(get(), get()) }
    single { FavouriteRecipesRepositoryImpl(get()) }
    single { MainRepositoryImpl(get()) }

}

val apiModule = module {
    single {
        val builder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
        builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        builder.build()
    }
    single {
        val type = object : TypeToken<DateTime>() {}.type
        GsonBuilder().registerTypeAdapter(type, DateTimeConverters()).create()
    }
    factory { params ->
        Retrofit.Builder()
            .client(get())
            .baseUrl(params.get<String>(0))
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single { get<Retrofit> { parametersOf(BuildConfig.SERVER_URL) }.create(RecipeApiService::class.java) }
}
