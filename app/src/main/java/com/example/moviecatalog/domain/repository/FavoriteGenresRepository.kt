package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.domain.model.Genre

interface FavoriteGenresRepository {

    fun getGenres(): List<Genre>

    fun addGenre(genre: Genre)

    fun deleteGenre(genre: Genre)

}