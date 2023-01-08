package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.ApiRepository
import java.lang.IllegalArgumentException

class MainSearchViewModelFactory constructor(val apiRepository: ApiRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainSearchViewModel::class.java)){
            MainSearchViewModel(this.apiRepository) as T
        }
        else{
            throw IllegalArgumentException("MainSearchViewModel not found")
        }
    }
}