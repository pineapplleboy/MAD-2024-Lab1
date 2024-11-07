package com.example.moviecatalog.app.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.moviecatalog.R
import com.example.moviecatalog.app.presentation.viewholder.FriendViewHolder
import com.example.moviecatalog.app.presentation.viewmodel.FriendsViewModel
import com.example.moviecatalog.domain.model.UserShort

class FriendAdapter(
    private val vm: FriendsViewModel
) : ListAdapter<UserShort, FriendViewHolder>(
    DIFF
) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<UserShort>() {
            override fun areItemsTheSame(oldItem: UserShort, newItem: UserShort): Boolean {
                return oldItem.userId == newItem.userId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: UserShort, newItem: UserShort): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        return FriendViewHolder(
            view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.friend_preview, parent, false),
            vm = vm
        )
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val userShort = getItem(position)
        holder.bind(userShort)
    }
}