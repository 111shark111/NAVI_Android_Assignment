package com.example.naviassignment.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.naviassignment.models.APIResponse
import com.example.naviassignment.retrofit.APIService

class MainRepository(private val apiService: APIService) {
    private val requestListData = MutableLiveData<APIResponse>()
    val requestData : LiveData<APIResponse>
    get() = requestListData
    suspend fun getClosedPullReqs(ownerName:String? , repoName:String? , pageNumber:Int){
        val result = apiService.getClosedPullRequests(ownerName,repoName,pageNumber);
        if(result.body()!=null){
            requestListData.postValue(result.body())
            Log.i("check","response is Coming from pageNumber - $pageNumber is this  ${result.body()}")
        }
        else{
            Log.i("check","response is failing at pageNumber - $pageNumber ")
        }

    }
}