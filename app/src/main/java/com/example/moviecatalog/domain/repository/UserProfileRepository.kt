package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.domain.model.UserRegister

interface UserProfileRepository {
    fun register(userRegister: UserRegister, callback: ((Result<String>) -> Unit))
}