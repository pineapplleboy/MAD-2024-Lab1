package com.example.moviecatalog.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieApiInstance {
    fun createApi(): MovieApi{
        return Retrofit.Builder()
            .baseUrl("https://react-midterm.kreosoft.space/api/movies/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }
}