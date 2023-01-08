package com.example.githubspeer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.githubspeer.data.UserFollowDataListItem
import com.example.githubspeer.databinding.ActivityMainBinding
import com.example.githubspeer.databinding.ActivityProfileViewBinding

class ProfileViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileViewBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        val userFollowList= intent.getParcelableExtra<UserFollowDataListItem>("follower")
        binding.tvLogin.text = userFollowList?.login
        binding.tvId.text = userFollowList?.id.toString()
        binding.tvUrl.text = userFollowList?.url

        Glide.with(this).load(userFollowList?.avatar_url).into(binding.imgUser);
    }
}