package com.example.naviassignment.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Constants {
    private const val BASE_URL = "https://api.github.com/";

    fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}