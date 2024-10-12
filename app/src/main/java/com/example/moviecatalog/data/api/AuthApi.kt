package com.example.moviecatalog.data.api

import com.example.moviecatalog.data.model.ApiLoginCredentials
import com.example.moviecatalog.data.model.ApiToken
import com.example.moviecatalog.data.model.ApiUserRegister
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    fun login(@Body loginCredentials: ApiLoginCredentials): Call<ApiToken>

    @POST("register")
    fun register(@Body userRegister: ApiUserRegister): Call<ApiToken>

    @POST("logout")
    fun logout(): Call<Void>
}