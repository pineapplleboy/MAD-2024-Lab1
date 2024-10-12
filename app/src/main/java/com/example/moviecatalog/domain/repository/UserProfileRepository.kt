package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.domain.model.UserProfile

interface UserProfileRepository {
    fun getProfile(callback: (Result<UserProfile>) -> Unit)
}