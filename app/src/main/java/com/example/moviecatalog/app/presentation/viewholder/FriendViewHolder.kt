package com.example.moviecatalog.app.presentation.viewholder

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalog.R
import com.example.moviecatalog.app.presentation.ui.activity.MovieDetailsActivity
import com.example.moviecatalog.app.presentation.viewmodel.FriendsViewModel
import com.example.moviecatalog.data.preferences.FriendsPreferences
import com.example.moviecatalog.data.repository.FriendsRepositoryImpl
import com.example.moviecatalog.databinding.FriendPreviewBinding
import com.example.moviecatalog.databinding.MoviePreviewBinding
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.model.UserShort
import com.example.moviecatalog.domain.usecase.friends.DeleteFriendUseCase
import com.example.moviecatalog.domain.usecase.movies.GetMovieRatingUseCase

class FriendViewHolder(
    val view: View,
    val vm: FriendsViewModel
) : RecyclerView.ViewHolder(view) {

    private val binding = FriendPreviewBinding.bind(view)
    private var friend: UserShort? = null

    init{
        binding.deleteFriendButton.setOnClickListener{
            friend?.let { it1 -> vm.deleteFriend(it1) }
        }
    }

    fun bind(user: UserShort) = with(binding){

        friend = user

        if(user.avatar != null && user.avatar != ""){
            Glide.with(view)
                .load(user.avatar)
                .into(avatar)
        }
        else{
            avatar.setImageResource(R.drawable.default_profile_icon)
        }

        name.text = user.nickName
    }

}