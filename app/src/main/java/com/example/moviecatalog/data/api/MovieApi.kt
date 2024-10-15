package com.example.moviecatalog.data.api

import com.example.moviecatalog.data.model.ApiMoviesPagedList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("{page}")
    fun getMoviesByPage(@Path("page") page: Int): Call<ApiMoviesPagedList>
}