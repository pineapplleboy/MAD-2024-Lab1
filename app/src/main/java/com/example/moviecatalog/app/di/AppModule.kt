package com.example.moviecatalog.app.di

import com.example.moviecatalog.app.presentation.viewmodel.FavoritesViewModel
import com.example.moviecatalog.app.presentation.viewmodel.MovieDetailsViewModel
import com.example.moviecatalog.app.presentation.viewmodel.MoviesViewModel
import com.example.moviecatalog.app.presentation.viewmodel.SignInViewModel
import com.example.moviecatalog.app.presentation.viewmodel.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{

    viewModel<MoviesViewModel>{
        MoviesViewModel(
            getTopMoviesUseCase = get(),
            getMoviesPageUseCase = get()
        )
    }

    viewModel<SignUpViewModel>{
        SignUpViewModel(
            signUpUseCase = get()
        )
    }

    viewModel<SignInViewModel>{
        SignInViewModel(
            signInUseCase = get()
        )
    }

    viewModel<MovieDetailsViewModel>{
        MovieDetailsViewModel(
            getMovieDetailsUseCase = get(),
            getFavoritesUseCase = get(),
            addToFavoritesUseCase = get(),
            deleteFromFavoritesUseCase = get(),
            getFavoriteGenresUseCase = get(),
            addFavoriteGenreUseCase = get(),
            deleteFavoriteGenreUseCase = get()
        )
    }

    viewModel<FavoritesViewModel>{
        FavoritesViewModel(
            getFavoritesUseCase = get(),
            getFavoriteGenresUseCase = get(),
            deleteFavoriteGenreUseCase = get()
        )
    }
}