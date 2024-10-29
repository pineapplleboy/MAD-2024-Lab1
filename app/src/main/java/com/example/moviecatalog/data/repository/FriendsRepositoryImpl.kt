package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.preferences.FriendsPreferences
import com.example.moviecatalog.domain.model.UserShort
import com.example.moviecatalog.domain.repository.FriendsRepository

class FriendsRepositoryImpl(
    private val friendsPreferences: FriendsPreferences
): FriendsRepository {

    override fun getFriends(): List<UserShort> {
        return friendsPreferences.getFriends()
    }

    override fun addFriend(user: UserShort) {
        return friendsPreferences.addFriend(user)
    }

    override fun deleteFriend(user: UserShort) {
        return friendsPreferences.deleteFriend(user)
    }
}