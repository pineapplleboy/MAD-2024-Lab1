package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.domain.model.UserProfile

interface UserProfileRepository {
    suspend fun getProfile(): Result<UserProfile>
}