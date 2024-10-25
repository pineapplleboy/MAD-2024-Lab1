package com.example.moviecatalog.domain.usecase

import android.util.Log
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.model.MoviesPagedList
import com.example.moviecatalog.domain.repository.MovieRepository

class GetMoviesPageUseCase(
    private val repository: MovieRepository
) {
    suspend fun execute(page: Int): Result<MoviesPagedList> {
        return repository.getMoviesByPage(page)
    }
}