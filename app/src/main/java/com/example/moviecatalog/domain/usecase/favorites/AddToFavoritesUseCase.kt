package com.example.moviecatalog.domain.usecase.favorites

import android.util.Log
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.repository.FavoritesRepository

class AddToFavoritesUseCase(
    private val repository: FavoritesRepository
) {
    suspend fun execute(id: String): Result<Unit> {
        return repository.add(id)
    }
}