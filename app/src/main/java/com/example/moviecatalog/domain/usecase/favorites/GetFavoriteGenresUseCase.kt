package com.example.moviecatalog.domain.usecase.favorites

import com.example.moviecatalog.domain.model.Genre
import com.example.moviecatalog.domain.repository.FavoriteGenresRepository

class GetFavoriteGenresUseCase(
    private val repository: FavoriteGenresRepository
){

    fun execute(): List<Genre> {
        return repository.getGenres()
    }
}