package com.example.moviecatalog.domain.usecase

import android.util.Log
import com.example.moviecatalog.domain.model.LoginCredentials
import com.example.moviecatalog.domain.repository.AuthRepository

class SignInUseCase(
    private val repository: AuthRepository
) {

    fun execute(loginCredentials: LoginCredentials, callback: (Boolean) -> Unit){
        repository.login(loginCredentials) { result ->
            result.onSuccess {
                Log.d("API", it)
                callback(true)
            }.onFailure {
                Log.e("API", it.message ?: "unknown error")
                callback(false)
            }
        }
    }
}