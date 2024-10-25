package com.example.moviecatalog.domain.usecase

import android.util.Log
import com.example.moviecatalog.domain.repository.FavoritesRepository

class DeleteFromFavoritesUseCase(
    private val repository: FavoritesRepository
) {
    suspend fun execute(id: String): Result<Unit> {
        return repository.delete(id)
    }
}