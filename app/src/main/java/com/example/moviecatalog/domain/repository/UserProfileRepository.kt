package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.domain.model.LoginCredentials
import com.example.moviecatalog.domain.model.UserRegister

interface UserProfileRepository {
    fun register(userRegister: UserRegister, callback: ((Result<String>) -> Unit))

    fun login(loginCredentials: LoginCredentials, callback: (Result<String>) -> Unit)

    fun logout(callback: (Result<String>) -> Unit)
}