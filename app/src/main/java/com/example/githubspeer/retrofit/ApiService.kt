package com.example.myapplication.retrofit

import com.example.githubspeer.data.UserFollowDataListItem
import com.example.myapplication.data.UserDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users/{query}")
    suspend fun getUsersList(@Path("query") query: String): Response<UserDetails>


    @GET("users/{query}/followers")
    suspend fun getUsersFollowersList(@Path("query") query: String): Response<ArrayList<UserFollowDataListItem>>

    @GET("users/{query}/following")
    suspend fun getUsersFollowingList(@Path("query") query: String): Response<ArrayList<UserFollowDataListItem>>


}