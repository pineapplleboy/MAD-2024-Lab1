package com.example.moviecatalog.app.di

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
}