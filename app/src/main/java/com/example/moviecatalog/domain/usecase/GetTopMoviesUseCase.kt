package com.example.moviecatalog.domain.usecase

import android.util.Log
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.repository.MovieRepository

class GetTopMoviesUseCase(private val repository: MovieRepository) {
    fun execute(callback: (List<MovieElement>) -> Unit) {
        repository.getMoviesByPage(1){result ->
            result.onSuccess {
                it.movies?.let { it1 -> callback(it1.take(5)) }
            }
                .onFailure {
                    Log.e("API", it.message ?: "unknown error")
                }
        }
    }
}