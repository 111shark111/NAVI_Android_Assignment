package com.example.naviassignment.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.naviassignment.models.APIResponse
import com.example.naviassignment.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository,private val repoName : String? , private val userName : String?) : ViewModel() {

    private val _isLoading : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
    get() = _isLoading
    init {
         viewModelScope.launch(Dispatchers.IO) {
             repository.getClosedPullReqs(userName, repoName ,1)
         }
    }
    val requests : LiveData<APIResponse>
    get() = repository.requestData

     fun fetchData ( pageNumber : Int ){
         viewModelScope.launch(Dispatchers.IO) {
             _isLoading.postValue(true)
             repository.getClosedPullReqs(userName, repoName, pageNumber)
             _isLoading.postValue(false)
         }
    }

}