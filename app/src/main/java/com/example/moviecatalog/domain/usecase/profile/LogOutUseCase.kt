package com.example.moviecatalog.domain.usecase.profile

import android.util.Log
import com.example.moviecatalog.data.preferences.AuthPreferences
import com.example.moviecatalog.data.preferences.FriendsPreferences
import com.example.moviecatalog.data.preferences.GenresPreferences
import com.example.moviecatalog.data.preferences.MoviesPreferences
import com.example.moviecatalog.domain.repository.AuthRepository

class LogOutUseCase(
    private val repository: AuthRepository,
    private val friendsPreferences: FriendsPreferences,
    private val genresPreferences: GenresPreferences,
    private val moviesPreferences: MoviesPreferences
) {

    suspend fun execute(): Result<Unit>{

        friendsPreferences.clear()
        genresPreferences.clear()
        moviesPreferences.clear()

        return repository.logout()
    }
}