package com.example.moviecatalog.domain.usecase

import android.util.Log
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.repository.FavoritesRepository

class GetFavoritesUseCase(
    private val repository: FavoritesRepository
) {
    fun execute(callback: (List<MovieElement>) -> Unit){
        repository.get{result ->
            result.onSuccess {
                callback(it)
            }
                .onFailure {
                    Log.e("API", it.message ?: "unknown error")
                }
        }
    }
}