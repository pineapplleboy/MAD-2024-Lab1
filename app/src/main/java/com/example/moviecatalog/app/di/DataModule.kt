package com.example.moviecatalog.app.di

import com.example.moviecatalog.data.api.MovieCatalogApi
import com.example.moviecatalog.data.api.MovieCatalogApiInstance
import com.example.moviecatalog.data.preferences.AuthPreferences
import com.example.moviecatalog.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.domain.repository.MovieRepository
import org.koin.dsl.module

val dataModule = module{

    single<AuthPreferences> {
        AuthPreferences(context = get())
    }

    single<MovieCatalogApi> {
        MovieCatalogApiInstance.createApi(authPreferences = get())
    }

    single<MovieRepository> {
        MovieRepositoryImpl(api = get())
    }
}