package com.example.moviecatalog.domain.usecase

import android.util.Log
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.repository.MovieRepository

class GetTopMoviesUseCase(private val repository: MovieRepository) {
    suspend fun execute(): Result<List<MovieElement>> {
        val result = repository.getMoviesByPage(1)

        return result.mapCatching {
            it.movies.orEmpty().take(5)
        }
    }
}