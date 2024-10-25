package com.example.moviecatalog.domain.usecase

import android.util.Log
import com.example.moviecatalog.domain.repository.AuthRepository

class LogOutUseCase(
    private val repository: AuthRepository
) {

    suspend fun execute(): Result<Unit>{

        return repository.logout()
    }
}