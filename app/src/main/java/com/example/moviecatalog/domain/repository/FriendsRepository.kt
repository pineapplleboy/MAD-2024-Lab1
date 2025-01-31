package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.domain.model.UserShort

interface FriendsRepository {

    fun getFriends(): List<UserShort>

    fun addFriend(user: UserShort)

    fun deleteFriend(user: UserShort)
}