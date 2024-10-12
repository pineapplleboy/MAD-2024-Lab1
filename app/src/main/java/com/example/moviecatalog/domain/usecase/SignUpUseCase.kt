package com.example.moviecatalog.domain.usecase

import android.util.Log
import com.example.moviecatalog.domain.model.UserRegister
import com.example.moviecatalog.domain.repository.UserProfileRepository
import okhttp3.Response

class SignUpUseCase(
    private val repository: UserProfileRepository
) {

    fun execute(user: UserRegister, callback: (Boolean) -> Unit) {
        repository.register(user) { result ->
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