package com.example.moviecatalog.app.di


import com.example.moviecatalog.domain.usecase.GetMoviesPageUseCase
import com.example.moviecatalog.domain.usecase.GetTopMoviesUseCase
import org.koin.dsl.module

val domainModule = module{

    factory<GetTopMoviesUseCase> {
        GetTopMoviesUseCase(repository = get())
    }

    factory<GetMoviesPageUseCase> {
        GetMoviesPageUseCase(repository = get())
    }
}