package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubspeer.data.UserFollowDataList
import com.example.githubspeer.data.UserFollowDataListItem
import com.example.githubspeer.utils.Constants.RETRIVING_USER
import com.example.githubspeer.utils.Constants.RETRIVING_USER_FOLLOWERS
import com.example.githubspeer.utils.Constants.RETRIVING_USER_FOLLOWINGS

import com.example.myapplication.data.UserDetails
import com.example.myapplication.repository.ApiRepository
import kotlinx.coroutines.*
import retrofit2.Response
import java.security.Key

class MainSearchViewModel constructor(private val apiRepository: ApiRepository) : ViewModel() {
    val userDetailResponse = MutableLiveData<UserDetails>()
    val userFollowersListResponse = MutableLiveData<List<UserFollowDataListItem>>()
    val userFollowingListResponse = MutableLiveData<List<UserFollowDataListItem>>()

    private var handlejob: Job? = null
    val errorMsg = MutableLiveData<HashMap<String,String>>()
    val loading = MutableLiveData<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("General",
            "Exception :${throwable.localizedMessage}"
        )
    }


    private fun onError(type:String,msg: String) {
        val errorMap = HashMap<String,String>()
        errorMap.put("TYPE", type)
        errorMap.put("MSG",msg)
        errorMsg.value = errorMap
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        handlejob?.cancel()
    }


    fun getUserDetails(userName: String) {
        loading.value = true
        handlejob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val userResponse = apiRepository.getUsersList(name = userName)
            withContext(Dispatchers.Main) {
                if (userResponse.isSuccessful) {
                    userDetailResponse.postValue(userResponse.body())
                    loading.value = false
                } else {
                    onError(RETRIVING_USER,"Error:${userResponse.message()}")
                }
            }

        }
    }

    fun getUserDetails() : UserDetails?{
        return userDetailResponse.value
    }

    fun getUserFollowersDetails(userName: String) {
        loading.value = true
        handlejob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val userFollowersResponse = apiRepository.getUsersFollowersList(name = userName)
            withContext(Dispatchers.Main) {
                if (userFollowersResponse.isSuccessful) {
                    userFollowersListResponse.postValue(userFollowersResponse.body())
                    loading.value = false
                } else {
                    onError(RETRIVING_USER_FOLLOWERS,"Error:${userFollowersResponse.message()}")
                }
            }

        }
    }

    fun getUserFollowingDetails(userName: String) {
        loading.value = true
        handlejob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val userFollowersResponse = apiRepository.getUsersFollowingList(name = userName)
            withContext(Dispatchers.Main) {
                if (userFollowersResponse.isSuccessful) {
                    userFollowingListResponse.postValue(userFollowersResponse.body())
                    loading.value = false
                } else {
                    onError(RETRIVING_USER_FOLLOWINGS,"Error:${userFollowersResponse.message()}")
                }
            }

        }
    }
}