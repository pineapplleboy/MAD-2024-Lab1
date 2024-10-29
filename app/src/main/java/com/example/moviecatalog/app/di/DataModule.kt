package com.example.moviecatalog.app.di

import com.example.moviecatalog.data.api.MovieCatalogApi
import com.example.moviecatalog.data.api.MovieCatalogApiInstance
import com.example.moviecatalog.data.preferences.AuthPreferences
import com.example.moviecatalog.data.preferences.FriendsPreferences
import com.example.moviecatalog.data.preferences.GenresPreferences
import com.example.moviecatalog.data.repository.AuthRepositoryImpl
import com.example.moviecatalog.data.repository.FavoriteGenresRepositoryImpl
import com.example.moviecatalog.data.repository.FavoritesRepositoryImpl
import com.example.moviecatalog.data.repository.FriendsRepositoryImpl
import com.example.moviecatalog.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.data.repository.UserProfileRepositoryImpl
import com.example.moviecatalog.domain.repository.AuthRepository
import com.example.moviecatalog.domain.repository.FavoriteGenresRepository
import com.example.moviecatalog.domain.repository.FavoritesRepository
import com.example.moviecatalog.domain.repository.FriendsRepository
import com.example.moviecatalog.domain.repository.MovieRepository
import com.example.moviecatalog.domain.repository.UserProfileRepository
import org.koin.dsl.module

val dataModule = module{

    single<AuthPreferences> {
        AuthPreferences(context = get())
    }

    single<GenresPreferences> {
        GenresPreferences(context = get())
    }

    single<FriendsPreferences> {
        FriendsPreferences(context = get())
    }

    single<MovieCatalogApi> {
        MovieCatalogApiInstance.createApi(authPreferences = get())
    }

    single<MovieRepository> {
        MovieRepositoryImpl(api = get())
    }

    single<AuthRepository> {
        AuthRepositoryImpl(authPreferences = get(), api = get())
    }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(api = get())
    }

    single<UserProfileRepository> {
        UserProfileRepositoryImpl(api = get())
    }

    single<FavoriteGenresRepository> {
        FavoriteGenresRepositoryImpl(genresPreferences = get())
    }

    single<FriendsRepository> {
        FriendsRepositoryImpl(friendsPreferences = get())
    }
}