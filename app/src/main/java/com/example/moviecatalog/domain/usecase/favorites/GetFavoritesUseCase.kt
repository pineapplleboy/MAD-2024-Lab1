package com.example.moviecatalog.domain.usecase.favorites

import android.util.Log
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.repository.FavoritesRepository

class GetFavoritesUseCase(
    private val repository: FavoritesRepository
) {
    suspend fun execute(): Result<List<MovieElement>>{
        return repository.get()
    }
}