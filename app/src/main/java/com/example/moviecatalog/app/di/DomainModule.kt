package com.example.moviecatalog.app.di


import com.example.moviecatalog.domain.usecase.favorites.AddFavoriteGenreUseCase
import com.example.moviecatalog.domain.usecase.favorites.AddToFavoritesUseCase
import com.example.moviecatalog.domain.usecase.favorites.DeleteFavoriteGenreUseCase
import com.example.moviecatalog.domain.usecase.favorites.DeleteFromFavoritesUseCase
import com.example.moviecatalog.domain.usecase.favorites.GetFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.favorites.GetFavoritesUseCase
import com.example.moviecatalog.domain.usecase.feed.GetNextUseCase
import com.example.moviecatalog.domain.usecase.friends.AddFriendUseCase
import com.example.moviecatalog.domain.usecase.friends.DeleteFriendUseCase
import com.example.moviecatalog.domain.usecase.friends.GetFriendsUseCase
import com.example.moviecatalog.domain.usecase.movies.GetMovieDetailsUseCase
import com.example.moviecatalog.domain.usecase.movies.GetMoviesPageUseCase
import com.example.moviecatalog.domain.usecase.movies.GetTopMoviesUseCase
import com.example.moviecatalog.domain.usecase.profile.GetUserProfileUseCase
import com.example.moviecatalog.domain.usecase.profile.LogOutUseCase
import com.example.moviecatalog.domain.usecase.profile.SignInUseCase
import com.example.moviecatalog.domain.usecase.profile.SignUpUseCase
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


    factory<GetFriendsUseCase> {
        GetFriendsUseCase(repository = get())
    }

    factory<AddFriendUseCase> {
        AddFriendUseCase(repository = get())
    }

    factory<DeleteFriendUseCase> {
        DeleteFriendUseCase(repository = get())
    }


    factory<GetNextUseCase> {
        GetNextUseCase(repository = get(), preferences = get())
    }
}