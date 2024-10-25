package com.example.moviecatalog.data.repository

import android.net.http.HttpException
import com.example.moviecatalog.data.api.FavoritesApi
import com.example.moviecatalog.data.model.ApiMovieDetails
import com.example.moviecatalog.data.model.ApiMovieElement
import com.example.moviecatalog.data.model.FavoriteMoviesApi
import com.example.moviecatalog.domain.model.MovieDetails
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.repository.FavoritesRepository
import retrofit2.Call
import retrofit2.Response

class FavoritesRepositoryImpl(
    private val favoritesApi: FavoritesApi
) : FavoritesRepository {

    override fun get(callback: (Result<List<MovieElement>>) -> Unit) {
        favoritesApi.get().enqueue(object : retrofit2.Callback<FavoriteMoviesApi>{

            override fun onResponse(
                call: Call<FavoriteMoviesApi>,
                response: Response<FavoriteMoviesApi>
            ) {
                if(response.isSuccessful){
                    val movies = response.body()
                    if(movies != null){
                        callback(Result.success(movies.movies.toDomainModelList()))
                    }
                }
                else{
                    val errorBody = response.errorBody()?.string()
                    callback(Result.failure(Exception("Getting favorites failed: $errorBody")))
                }
            }

            override fun onFailure(call: Call<FavoriteMoviesApi>, t: Throwable) {
                callback(Result.failure(Exception("Network error: ${t.message}")))
            }
        })
    }

    override fun add(id: String, callback: (Result<Unit>) -> Unit) {
        favoritesApi.add(id).enqueue(object: retrofit2.Callback<Unit>{

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    callback(Result.success(Unit))
                }
                else{
                    val errorBody = response.errorBody()?.string()
                    callback(Result.failure(Exception("Adding favorite failed: $errorBody")))
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                callback(Result.failure(Exception("Network error: ${t.message}")))
            }
        })
    }

    override fun delete(id: String, callback: (Result<Unit>) -> Unit) {
        favoritesApi.delete(id).enqueue(object: retrofit2.Callback<Unit>{

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    callback(Result.success(Unit))
                }
                else{
                    val errorBody = response.errorBody()?.string()
                    callback(Result.failure(Exception("Deleting favorite failed: $errorBody")))
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                callback(Result.failure(Exception("Network error: ${t.message}")))
            }
        })
    }
}