package com.example.moviecatalog.domain.usecase.profile

import android.util.Log
import com.example.moviecatalog.domain.model.LoginCredentials
import com.example.moviecatalog.domain.repository.AuthRepository

class SignInUseCase(
    private val repository: AuthRepository
) {

    suspend fun execute(loginCredentials: LoginCredentials): Result<Unit> {
        return repository.login(loginCredentials)
    }
}