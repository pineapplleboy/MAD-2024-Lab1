package com.example.moviecatalog.app.di

import com.example.moviecatalog.data.api.MovieApi
import com.example.moviecatalog.data.api.MovieApiInstance
import com.example.moviecatalog.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.domain.repository.MovieRepository
import org.koin.dsl.module

val dataModule = module{

    single<MovieApi> {
        MovieApiInstance.createApi()
    }

    single<MovieRepository> {
        MovieRepositoryImpl(movieApi = get())
    }
}