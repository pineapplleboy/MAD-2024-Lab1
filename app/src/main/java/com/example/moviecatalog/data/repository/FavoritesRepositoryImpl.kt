package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.MovieCatalogApi
import com.example.moviecatalog.data.safeApiCall
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.repository.FavoritesRepository

class FavoritesRepositoryImpl(
    private val api: MovieCatalogApi
) : FavoritesRepository {

    override suspend fun get(): Result<List<MovieElement>> {

        return safeApiCall(
            apiCall = {
                api.get().execute()
            },
            transform = {
                it.movies.toDomainModelList()
            }
        )
    }

    override suspend fun add(id: String): Result<Unit> {

        return safeApiCall(
            apiCall = {
                api.add(id).execute()
            },
            transform = {}
        )
    }

    override suspend fun delete(id: String): Result<Unit> {

        return safeApiCall(
            apiCall = {
                api.delete(id).execute()
            },
            transform = {}
        )
    }
}