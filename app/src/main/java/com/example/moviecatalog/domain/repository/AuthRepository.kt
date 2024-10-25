package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.domain.model.LoginCredentials
import com.example.moviecatalog.domain.model.UserRegister

interface AuthRepository {
    suspend fun register(userRegister: UserRegister): Result<Unit>

    suspend fun login(loginCredentials: LoginCredentials): Result<Unit>

    suspend fun logout(): Result<Unit>
}