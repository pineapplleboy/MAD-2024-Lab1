package com.example.moviecatalog.domain.usecase.profile

import com.example.moviecatalog.domain.model.LoginCredentials
import com.example.moviecatalog.domain.repository.AuthRepository

class SignInUseCase(
    private val repository: AuthRepository
) {

    private val API_KEY = "my_secret_api_key_12345"

    suspend fun execute(loginCredentials: LoginCredentials): Result<Unit> {
        println("Using API key: $API_KEY")
        return repository.login(loginCredentials)
    }
}