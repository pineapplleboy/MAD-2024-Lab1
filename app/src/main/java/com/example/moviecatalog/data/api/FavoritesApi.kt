package com.example.moviecatalog.data.api

import com.example.moviecatalog.data.model.ApiLogOutMsg
import com.example.moviecatalog.data.model.ApiLoginCredentials
import com.example.moviecatalog.data.model.ApiMovieElement
import com.example.moviecatalog.data.model.ApiToken
import com.example.moviecatalog.data.model.ApiUserProfile
import com.example.moviecatalog.data.model.ApiUserRegister
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoritesApi {

    @POST("")
    fun get(): Call<List<ApiMovieElement>>

    @POST("add/{id}")
    fun add(@Path("id") id: String): Call<Unit>

    @POST("delete/{id}")
    fun delete(@Path("id") id: String): Call<Unit>
}