package com.example.githubspeer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubspeer.adapter.RecyclerAdapter
import com.example.githubspeer.data.UserFollowDataList
import com.example.githubspeer.data.UserFollowDataListItem
import com.example.githubspeer.databinding.ActivityFollowsBinding
import com.example.githubspeer.databinding.ActivityMainBinding
import com.example.myapplication.repository.ApiRepository
import com.example.myapplication.retrofit.RetrofitInstance

class FollowsActivity : AppCompatActivity() {
    lateinit var binding: ActivityFollowsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFollowsBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        val userFollowList= intent.getParcelableArrayListExtra<UserFollowDataListItem>("followList")

        val RecyclerAdapter = userFollowList?.let { RecyclerAdapter(this@FollowsActivity,it) }
        val layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = RecyclerAdapter

        RecyclerAdapter?.onItemClick = { follower ->
            val intent = Intent(this,ProfileViewActivity::class.java)
            intent.putExtra("follower",follower)
            startActivity(intent)            // do something with your item
        }
    }
}