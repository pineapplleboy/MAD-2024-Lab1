package com.example.moviecatalog.app.di


import com.example.moviecatalog.domain.usecase.AddFavoriteGenreUseCase
import com.example.moviecatalog.domain.usecase.AddToFavoritesUseCase
import com.example.moviecatalog.domain.usecase.DeleteFavoriteGenreUseCase
import com.example.moviecatalog.domain.usecase.DeleteFromFavoritesUseCase
import com.example.moviecatalog.domain.usecase.GetFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.GetFavoritesUseCase
import com.example.moviecatalog.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.domain.usecase.GetMoviesPageUseCase
import com.example.moviecatalog.domain.usecase.GetTopMoviesUseCase
import com.example.moviecatalog.domain.usecase.GetUserProfileUseCase
import com.example.moviecatalog.domain.usecase.LogOutUseCase
import com.example.moviecatalog.domain.usecase.SignInUseCase
import com.example.moviecatalog.domain.usecase.SignUpUseCase
import org.koin.dsl.module

val domainModule = module{

    factory<GetTopMoviesUseCase> {
        GetTopMoviesUseCase(repository = get())
    }

    factory<GetMoviesPageUseCase> {
        GetMoviesPageUseCase(repository = get())
    }

    factory<GetMovieDetailsUseCase> {
        GetMovieDetailsUseCase(repository = get())
    }


    factory<AddToFavoritesUseCase> {
        AddToFavoritesUseCase(repository = get())
    }

    factory<DeleteFromFavoritesUseCase> {
        DeleteFromFavoritesUseCase(repository = get())
    }

    factory<GetFavoritesUseCase> {
        GetFavoritesUseCase(repository = get())
    }


    factory<GetUserProfileUseCase> {
        GetUserProfileUseCase(repository = get())
    }

    factory<LogOutUseCase> {
        LogOutUseCase(repository = get())
    }

    factory<SignInUseCase> {
        SignInUseCase(repository = get())
    }

    factory<SignUpUseCase> {
        SignUpUseCase(repository = get())
    }


    factory<GetFavoriteGenresUseCase> {
        GetFavoriteGenresUseCase(repository = get())
    }

    factory<AddFavoriteGenreUseCase> {
        AddFavoriteGenreUseCase(repository = get())
    }

    factory<DeleteFavoriteGenreUseCase> {
        DeleteFavoriteGenreUseCase(repository = get())
    }
}