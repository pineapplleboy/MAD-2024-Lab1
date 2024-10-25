package com.example.moviecatalog.domain.usecase

import android.util.Log
import com.example.moviecatalog.domain.model.UserRegister
import com.example.moviecatalog.domain.repository.AuthRepository

class SignUpUseCase(
    private val repository: AuthRepository
) {

    suspend fun execute(user: UserRegister): Result<Unit> {
        return repository.register(user)
    }
}