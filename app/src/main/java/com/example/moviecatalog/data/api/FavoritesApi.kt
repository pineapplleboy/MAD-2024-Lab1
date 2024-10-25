package com.example.moviecatalog.data.api

import com.example.moviecatalog.data.model.ApiLogOutMsg
import com.example.moviecatalog.data.model.ApiLoginCredentials
import com.example.moviecatalog.data.model.ApiMovieElement
import com.example.moviecatalog.data.model.ApiToken
import com.example.moviecatalog.data.model.ApiUserProfile
import com.example.moviecatalog.data.model.ApiUserRegister
import com.example.moviecatalog.data.model.FavoriteMoviesApi
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoritesApi {

    @GET(".")
    fun get(): Call<FavoriteMoviesApi>

    @POST("{id}/add")
    fun add(@Path("id") id: String): Call<Unit>

    @POST("{id}/delete")
    fun delete(@Path("id") id: String): Call<Unit>
}