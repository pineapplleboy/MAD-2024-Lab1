package com.example.moviecatalog.domain.usecase

import android.util.Log
import androidx.compose.runtime.Composable
import com.example.moviecatalog.domain.model.MovieDetails
import com.example.moviecatalog.domain.repository.MovieRepository

class GetMovieDetailsUseCase(
    private val repository: MovieRepository
) {
    fun execute(id: String, callback: (MovieDetails) -> Unit){
        repository.getMovieDetails(id){result ->
            result.onSuccess {
                callback(it)
            }
                .onFailure {
                    Log.e("API", it.message ?: "unknown error")
                }
        }
    }
}