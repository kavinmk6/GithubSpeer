package com.example.myapplication.repository

import com.example.myapplication.retrofit.ApiService
import com.example.myapplication.retrofit.RetrofitInstance

class ApiRepository(val apiService: ApiService) {

//    private val apiService = RetrofitInstance.getApiService();
    suspend fun getUsersList(name : String) = apiService.getUsersList(query = name)

    suspend fun getUsersFollowersList(name : String) = apiService.getUsersFollowersList(query = name)

    suspend fun getUsersFollowingList(name : String) = apiService.getUsersFollowingList(query = name)


}