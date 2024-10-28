package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.model.Genre
import com.example.moviecatalog.domain.repository.FavoriteGenresRepository

class AddFavoriteGenreUseCase(
    private val repository: FavoriteGenresRepository
) {

    fun execute(genre: Genre){
        repository.addGenre(genre)
    }
}