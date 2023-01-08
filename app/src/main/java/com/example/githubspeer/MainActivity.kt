package com.example.githubspeer

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log.v
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.githubspeer.data.UserFollowDataListItem
import com.example.githubspeer.databinding.ActivityMainBinding
import com.example.githubspeer.utils.Constants.RETRIVING_USER
import com.example.githubspeer.utils.Constants.RETRIVING_USER_FOLLOWERS
import com.example.githubspeer.utils.Constants.RETRIVING_USER_FOLLOWINGS
import com.example.myapplication.data.UserDetails
import com.example.myapplication.repository.ApiRepository
import com.example.myapplication.retrofit.RetrofitInstance
import com.example.myapplication.viewmodel.MainSearchViewModel
import com.example.myapplication.viewmodel.MainSearchViewModelFactory

class MainActivity : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        val apiService = RetrofitInstance.getApiService()
        val apiRepository = ApiRepository(apiService)

        viewModel = ViewModelProvider(this@MainActivity, MainSearchViewModelFactory(apiRepository)).get(
            MainSearchViewModel::class.java
        )

        binding.imgbtSearchUser.setOnClickListener(this);
        binding.userDetailLayout.tvFollowers.setOnClickListener(this);
        binding.userDetailLayout.tvFollowing.setOnClickListener(this);

       observingViewmodels()
    }

    fun observingViewmodels(){
        viewModel.userFollowersListResponse.observe(this) {
            navigateNextActivity(it)
        }
        viewModel.errorMsg.observe(this) {error ->
            for (type in error.keys){

                if (type.equals(RETRIVING_USER)){
                    setVisibility(binding.tvNoResults, true)
                    setVisibility(binding.cardUserLayout, false)
                    break
                }
                else if (type.equals(RETRIVING_USER_FOLLOWERS)){
                    showToast("Error happened, Please try again.....")
                }
                else if (type.equals(RETRIVING_USER_FOLLOWINGS)){
                    showToast("Error happened, Please try again.....")
                }

            }
        }
        viewModel.userDetailResponse.observe(this) {
            setDatas(it)
        }

        viewModel.errorMsg.observe(this) {
            setVisibility(binding.tvNoResults, true)
            setVisibility(binding.cardUserLayout, false)
        }
        viewModel.userFollowingListResponse.observe(this) {
            navigateNextActivity(it)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imgbt_search_user -> {
                val userName = binding.etSearchUser.text.toString()
                if (!TextUtils.isEmpty(userName)) {
                    viewModel.getUserDetails(userName)
                } else {
                    binding.etSearchUser.error = "Please enter a Valid username"
                    return
                }
            }

            R.id.tv_followers -> {
                if (viewModel.getUserDetails()!!.followers != 0) {
                    viewModel.getUserDetails()?.let { viewModel.getUserFollowersDetails(it.login) }
                } else {
                    showToast("Profile doesn't have followers yet.....")
                }
            }

            R.id.tv_following -> {
                if (viewModel.getUserDetails()!!.following != 0) {
                    viewModel.getUserDetails()?.let { viewModel.getUserFollowingDetails(it.login) }
                } else {
                    showToast("Profile doesn't follow anyone yet.....")
                }
            }
        }
        viewModel.loading.observe(this) {
            if (it) binding.progressBar.visibility =
                View.VISIBLE else binding.progressBar.visibility = View.GONE
        }
    }

    fun navigateNextActivity(list: List<UserFollowDataListItem>) {
        val alist = ArrayList(list)
        Toast.makeText(this@MainActivity, "" + alist.size, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, FollowsActivity::class.java)
        intent.putParcelableArrayListExtra("followList", alist)
        startActivity(intent)
    }

    //Due to time shortage, i have encoded all strings. Prefers to do it in a design and also utilizing Strings.xml
    fun setDatas(user: UserDetails) {
        setVisibility(binding.tvNoResults, false)
        setVisibility(binding.cardUserLayout, true)
        setImageUrl(user.avatar_url)
        binding.userDetailLayout.tvUsername.text = "UserName : ${user.name ?: "----"}"
        binding.userDetailLayout.tvName.text = "Name : ${user.login ?: "----"}"
        binding.userDetailLayout.tvDescription.text = "Description : ${user.bio ?: "----"}"
        binding.userDetailLayout.tvFollowers.text =
            "Followers : ${user.followers.toString() ?: "----"}"
        binding.userDetailLayout.tvFollowing.text =
            "Following : ${user.following.toString() ?: "----"}"
    }

    fun setImageUrl(url: String?) {
        Glide.with(this).load(url).into(binding.userDetailLayout.imgUser);
    }

    //Prefers extension function for all the below function while live projects
    fun setVisibility(view: View, isVisible: Boolean) {
        if (isVisible) view.visibility = View.VISIBLE else view.visibility = View.GONE
    }
    fun showToast(message: String){
        Toast.makeText(
            this@MainActivity,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

}