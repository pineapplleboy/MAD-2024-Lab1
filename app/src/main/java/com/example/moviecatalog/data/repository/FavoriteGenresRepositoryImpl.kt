package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.preferences.GenresPreferences
import com.example.moviecatalog.domain.model.Genre
import com.example.moviecatalog.domain.repository.FavoriteGenresRepository

class FavoriteGenresRepositoryImpl(
    private val genresPreferences: GenresPreferences
) : FavoriteGenresRepository {

    override fun getGenres(): List<Genre> {
        return genresPreferences.getGenres()
    }

    override fun addGenre(genre: Genre) {
        genresPreferences.addGenre(genre)
    }

    override fun deleteGenre(genre: Genre) {
        genresPreferences.deleteGenre(genre)
    }

}