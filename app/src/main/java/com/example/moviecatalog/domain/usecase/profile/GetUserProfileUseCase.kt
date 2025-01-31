package com.example.moviecatalog.domain.usecase.profile

import android.util.Log
import com.example.moviecatalog.domain.model.UserProfile
import com.example.moviecatalog.domain.repository.UserProfileRepository

class GetUserProfileUseCase(private val repository: UserProfileRepository) {

    suspend fun execute(): Result<UserProfile>{
        return repository.getProfile()
    }
}