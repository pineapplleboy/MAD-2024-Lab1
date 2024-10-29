package com.example.moviecatalog.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.moviecatalog.domain.model.UserShort
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FriendsPreferences(
    context: Context
) {
    companion object {
        private const val PREFS_NAME = "friends_prefs"
        private const val GENRES_TOKEN_KEY = "friends_token"
    }

    private val gson = Gson()

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getFriends(): List<UserShort> {
        val json = sharedPreferences.getString(GENRES_TOKEN_KEY, null)
        return if (json.isNullOrEmpty()) {
            emptyList()
        } else {
            val type = object : TypeToken<List<UserShort>>() {}.type
            gson.fromJson(json, type)
        }
    }

    fun addFriend(user: UserShort) {
        val friends = getFriends().toMutableList()
        if(friends.find {it.userId == user.userId} == null){
            friends.add(user)
            saveFriends(friends)
        }
    }

    fun deleteFriend(user: UserShort) {
        val friends = getFriends().toMutableList()
        friends.removeAll { it.userId == user.userId }
        saveFriends(friends)
    }

    private fun saveFriends(friends: List<UserShort>) {
        val editor = sharedPreferences.edit()
        editor.putString(GENRES_TOKEN_KEY, gson.toJson(friends))
        editor.apply()
    }
}