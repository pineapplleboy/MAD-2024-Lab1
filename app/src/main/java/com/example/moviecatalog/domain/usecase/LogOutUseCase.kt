package com.example.moviecatalog.domain.usecase

import android.util.Log
import com.example.moviecatalog.domain.repository.UserProfileRepository

class LogOutUseCase(
    private val repository: UserProfileRepository
) {

    fun execute(callback: (Boolean) -> Unit){

        repository.logout { result ->
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