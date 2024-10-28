package com.example.moviecatalog.data.api

import com.example.moviecatalog.data.model.ApiLogOutMsg
import com.example.moviecatalog.data.model.ApiLoginCredentials
import com.example.moviecatalog.data.model.ApiMovieDetails
import com.example.moviecatalog.data.model.ApiMoviesPagedList
import com.example.moviecatalog.data.model.ApiToken
import com.example.moviecatalog.data.model.ApiUserProfile
import com.example.moviecatalog.data.model.ApiUserRegister
import com.example.moviecatalog.data.model.FavoriteMoviesApi
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MovieCatalogApi {

    @POST("account/login")
    fun login(@Body loginCredentials: ApiLoginCredentials): Call<ApiToken>

    @POST("account/register")
    fun register(@Body userRegister: ApiUserRegister): Call<ApiToken>

    @POST("account/logout")
    fun logout(): Call<ApiLogOutMsg>

    @GET("account/profile")
    fun getProfile(): Call<ApiUserProfile>


    @GET("movies/{page}")
    fun getMoviesByPage(@Path("page") page: Int): Call<ApiMoviesPagedList>

    @GET("movies/details/{id}")
    fun getMovieDetails(@Path("id") id: String): Call<ApiMovieDetails>


    @GET("favorites")
    fun get(): Call<FavoriteMoviesApi>

    @POST("favorites/{id}/add")
    fun add(@Path("id") id: String): Call<Unit>

    @DELETE("favorites/{id}/delete")
    fun delete(@Path("id") id: String): Call<Unit>



}