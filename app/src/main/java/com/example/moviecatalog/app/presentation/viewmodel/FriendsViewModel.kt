package com.example.moviecatalog.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.domain.model.UserShort
import com.example.moviecatalog.domain.usecase.friends.AddFriendUseCase
import com.example.moviecatalog.domain.usecase.friends.DeleteFriendUseCase
import com.example.moviecatalog.domain.usecase.friends.GetFriendsUseCase

class FriendsViewModel(
    private val getFriendsUseCase: GetFriendsUseCase,
    private val addFriendUseCase: AddFriendUseCase,
    private val deleteFriendUseCase: DeleteFriendUseCase
) : ViewModel() {

    private val friendsMutable = MutableLiveData<List<UserShort>>()
    val friends: LiveData<List<UserShort>> get() = friendsMutable

    fun getFriends() {
        friendsMutable.value = getFriendsUseCase.execute()
    }

    fun deleteFriend(user: UserShort) {
        deleteFriendUseCase.execute(user)
        getFriends()
    }
}