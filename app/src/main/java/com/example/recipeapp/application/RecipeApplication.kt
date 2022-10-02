package com.example.recipeapp.application

import android.app.Application
import com.example.recipeapp.di.apiModule
import com.example.recipeapp.di.appModule
import com.example.recipeapp.di.repositoryModule
import com.example.recipeapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class RecipeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RecipeApplication)
            modules(
                listOf(
                    appModule,
                    repositoryModule,
                    viewModelModule,
                    apiModule
                )
            )
        }
    }
}