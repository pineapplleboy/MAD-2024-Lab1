package com.example.moviecatalog.domain.usecase.feed

import android.util.Log
import com.example.moviecatalog.data.preferences.MoviesPreferences
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.repository.MovieRepository

class GetNextUseCase(
    private val preferences: MoviesPreferences,
    private val repository: MovieRepository
) {

    suspend fun execute(): MovieElement? {
        val pageInfo = preferences.getPageInfo()

        var remainingMovies = preferences.getRemainingMovies()

        Log.d("API", pageInfo.toString())

        if(pageInfo == null){
            val moviesPagedList = repository.getMoviesByPage(1)

            moviesPagedList.onSuccess {
                preferences.changePage(it)

                remainingMovies = preferences.getRemainingMovies()
                if(remainingMovies.isNotEmpty()){
                    val movie = remainingMovies.random()
                    preferences.skipMovie(movie)

                    return movie
                }
            }
        }

        else if(remainingMovies.isNotEmpty()){
            val movie = remainingMovies.random()
            preferences.skipMovie(movie)

            return movie
        }

        else if(pageInfo.currentPage < pageInfo.pageCount){

            val moviesPagedList = repository.getMoviesByPage(pageInfo.currentPage + 1)

            moviesPagedList.onSuccess {
                preferences.changePage(it)

                remainingMovies = preferences.getRemainingMovies()
                if(remainingMovies.isNotEmpty()){
                    val movie = remainingMovies.random()
                    preferences.skipMovie(movie)

                    return movie
                }
            }
        }

        return null
    }
}