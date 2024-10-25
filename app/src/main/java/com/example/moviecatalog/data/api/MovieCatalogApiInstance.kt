package com.example.moviecatalog.data.api

import MovieCatalogApiInterceptor
import com.example.moviecatalog.data.preferences.AuthPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieCatalogApiInstance{

    private fun createClient(authPreferences: AuthPreferences): OkHttpClient{
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(MovieCatalogApiInterceptor(authPreferences))
            .build()
    }

    fun createApi(authPreferences: AuthPreferences): MovieCatalogApi{
        return Retrofit.Builder()
            .baseUrl("https://react-midterm.kreosoft.space/api/")
            .client(createClient(authPreferences))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieCatalogApi::class.java)
    }
}