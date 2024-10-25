package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.domain.model.MovieElement

interface FavoritesRepository {

    suspend fun get(): Result<List<MovieElement>>

    suspend fun add(id: String): Result<Unit>

    suspend fun delete(id: String): Result<Unit>
}