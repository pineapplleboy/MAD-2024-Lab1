package com.example.moviecatalog.domain.usecase

import android.util.Log
import com.example.moviecatalog.domain.model.UserProfile
import com.example.moviecatalog.domain.repository.UserProfileRepository

class GetUserProfileUseCase(private val repository: UserProfileRepository) {

    fun execute(callback: (UserProfile) -> Unit){
        repository.getProfile {result ->

            result.onSuccess {
                callback(it)
            }

                .onFailure {
                    Log.e("API", it.message ?: "unknown error")
                }
        }
    }
}