package com.example.moviecatalog.data.api

import AuthInterceptor
import com.example.moviecatalog.data.preferences.AuthPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FavoritesApiInstance {

    private fun createClient(authPreferences: AuthPreferences): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(AuthInterceptor(authPreferences))
            .build()
    }

    fun createApi(authPreferences: AuthPreferences): FavoritesApi{
        return Retrofit.Builder()
            .baseUrl("https://react-midterm.kreosoft.space/api/favorites/")
            .client(createClient(authPreferences))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FavoritesApi::class.java)
    }
}