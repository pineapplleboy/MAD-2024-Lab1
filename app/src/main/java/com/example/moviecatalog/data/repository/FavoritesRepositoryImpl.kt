package com.example.moviecatalog.data.repository

import android.net.http.HttpException
import com.example.moviecatalog.data.api.FavoritesApi
import com.example.moviecatalog.data.model.ApiMovieDetails
import com.example.moviecatalog.data.model.ApiMovieElement
import com.example.moviecatalog.data.model.FavoriteMoviesApi
import com.example.moviecatalog.data.safeApiCall
import com.example.moviecatalog.domain.model.MovieDetails
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.repository.FavoritesRepository
import retrofit2.Call
import retrofit2.Response

class FavoritesRepositoryImpl(
    private val favoritesApi: FavoritesApi
) : FavoritesRepository {

    override suspend fun get(): Result<List<MovieElement>> {

        return safeApiCall(
            apiCall = {
                favoritesApi.get().execute()
            },
            transform = {
                it.movies.toDomainModelList()
            }
        )
    }

    override suspend fun add(id: String): Result<Unit> {

        return safeApiCall(
            apiCall = {
                favoritesApi.add(id).execute()
            },
            transform = {}
        )
    }

    override suspend fun delete(id: String): Result<Unit> {

        return safeApiCall(
            apiCall = {
                favoritesApi.delete(id).execute()
            },
            transform = {}
        )
    }
}