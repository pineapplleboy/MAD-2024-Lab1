package com.example.moviecatalog.domain.usecase

import android.util.Log
import com.example.moviecatalog.domain.model.LoginCredentials
import com.example.moviecatalog.domain.repository.UserProfileRepository

class SignInUseCase(
    private val repository: UserProfileRepository
) {

    fun execute(loginCredentials: LoginCredentials){
        repository.login(loginCredentials) {result ->
            result.onSuccess {
                Log.d("API", it)
            }.onFailure {
                Log.e("API", it.message ?: "unknown error")
            }
        }
    }
}