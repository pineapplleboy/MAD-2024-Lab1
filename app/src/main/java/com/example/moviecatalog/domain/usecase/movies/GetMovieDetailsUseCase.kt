package com.example.moviecatalog.domain.usecase.movies

import android.util.Log
import androidx.compose.runtime.Composable
import com.example.moviecatalog.domain.model.MovieDetails
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.repository.MovieRepository

class GetMovieDetailsUseCase(
    private val repository: MovieRepository
) {
    suspend fun execute(id: String): Result<MovieDetails> {
        return repository.getMovieDetails(id)
    }
}