package com.example.naviassignment.retrofit


import com.example.naviassignment.models.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {

    @GET("/repos/{ownerName}/{repoName}/pulls")
    suspend fun getClosedPullRequests(@Path("ownerName") ownerName : String?,@Path("repoName") repoName : String? , @Query("page") pageNumber:Int ,@Query("per_page") perPage:Int = 10 , @Query("state") state:String = "closed") : Response<APIResponse>

}