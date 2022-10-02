package com.example.recipeapp.bases

import androidx.lifecycle.ViewModel
import com.example.recipeapp.common.CoroutineContextProvider
import kotlinx.coroutines.Job
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(KoinApiExtension::class)
abstract class BaseViewModel : ViewModel(), KoinComponent {
    val coroutineContextProvider by inject<CoroutineContextProvider>()
    val apiCallJobs = mutableListOf<Job>()

    override fun onCleared() {
        super.onCleared()
        apiCallJobs.forEach { it.cancel() }
    }

    fun cancelAllJobs() {
        apiCallJobs.forEach { it.cancel() }
    }
}