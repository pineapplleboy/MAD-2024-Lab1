package com.example.moviecatalog.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.domain.model.UserShort

class FriendsViewModel(

) : ViewModel() {

    private val friendsMutable = MutableLiveData<List<UserShort>>()
    val friends: LiveData<List<UserShort>> get() = friendsMutable

    fun getFriends(){
        
    }

}