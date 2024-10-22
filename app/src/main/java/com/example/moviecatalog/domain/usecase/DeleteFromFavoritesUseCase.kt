package com.example.moviecatalog.domain.usecase

import android.util.Log
import com.example.moviecatalog.domain.repository.FavoritesRepository

class DeleteFromFavoritesUseCase(
    private val repository: FavoritesRepository
) {
    fun execute(id: String, callback: () -> Unit) {
        repository.delete(id) { result ->
            result.onSuccess {
                callback()
            }
                .onFailure {
                    Log.e("API", it.message ?: "unknown error")
                }
        }
    }
}