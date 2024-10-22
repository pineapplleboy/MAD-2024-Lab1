package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.domain.model.MovieElement

interface FavoritesRepository {

    fun get(callback: (Result<List<MovieElement>>) -> Unit)

    fun add(id: String, callback: (Result<Unit>) -> Unit)

    fun delete(id: String, callback: (Result<Unit>) -> Unit)
}