package com.example.moviecatalog.data.preferences

import android.content.Context
import android.content.SharedPreferences

class AuthPreferences(context: Context) {

    companion object {
        private const val PREFS_NAME = "auth_prefs"
        private const val AUTH_TOKEN_KEY = "auth_token"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(AUTH_TOKEN_KEY, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(AUTH_TOKEN_KEY, null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove(AUTH_TOKEN_KEY).apply()
    }
}