package com.example.moviecatalog.domain.usecase

import android.util.Log
import com.example.moviecatalog.domain.model.MoviesPagedList
import com.example.moviecatalog.domain.repository.MovieRepository

class GetMoviesPageUseCase(
    private val repository: MovieRepository
) {
    fun execute(page: Int, callback: (MoviesPagedList) -> Unit) {
        repository.getMoviesByPage(page){ result ->
            result.onSuccess {
                callback(it)
            }
                .onFailure {
                    Log.e("API", it.message ?: "unknown error")
                }
        }
    }
}