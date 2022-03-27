package com.example.naviassignment.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.naviassignment.repository.MainRepository

class MainViewModelFactory(private val repository : MainRepository,private val repoName : String? , private val userName : String?) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository,repoName,userName) as T
    }

}