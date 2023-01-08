package com.example.githubspeer.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubspeer.R
import com.example.githubspeer.data.UserFollowDataListItem
import kotlin.coroutines.coroutineContext

internal class RecyclerAdapter(private val context: Context, private var followerList: List<UserFollowDataListItem>) :
RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>(){
    var onItemClick: ((UserFollowDataListItem) -> Unit)? = null

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.tv_username)
        var id: TextView = view.findViewById(R.id.tv_name)
        var description: TextView = view.findViewById(R.id.tv_description)
        var imageView: ImageView = view.findViewById(R.id.img_user)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(followerList[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.users_list_layout, parent, false)
        return MyViewHolder(itemView)    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = followerList[position]
        holder.name.text = "Login Name : ${item.login}"
        holder.id.text = "Login id : ${item.id}"
        Glide.with(context).load(item.avatar_url).into(holder.imageView);
    }

    override fun getItemCount(): Int {
        return followerList.size
    }
}