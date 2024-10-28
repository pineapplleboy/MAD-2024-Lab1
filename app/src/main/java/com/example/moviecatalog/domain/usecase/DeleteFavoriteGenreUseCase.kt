package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.model.Genre
import com.example.moviecatalog.domain.repository.FavoriteGenresRepository

class DeleteFavoriteGenreUseCase(
    private val repository: FavoriteGenresRepository
) {

    fun execute(genre: Genre){
        repository.deleteGenre(genre)
    }
}