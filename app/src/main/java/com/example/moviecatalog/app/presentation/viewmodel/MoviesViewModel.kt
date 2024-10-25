package com.example.moviecatalog.app.presentation.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.usecase.GetMoviesPageUseCase
import com.example.moviecatalog.domain.usecase.GetTopMoviesUseCase

class MoviesViewModel(
    private val getMoviesPageUseCase: GetMoviesPageUseCase,
    private val getTopMoviesUseCase: GetTopMoviesUseCase
) : ViewModel() {

    private val topMoviesMutable = MutableLiveData<List<MovieElement>>()
    val topMovies: LiveData<List<MovieElement>> get() = topMoviesMutable

    private val moviesMutable = MutableLiveData<List<MovieElement>>()
    val movies: LiveData<List<MovieElement>> get() = moviesMutable

    private val progressBarStateMutable = MutableLiveData<List<Int>>()

    private var currentPage = 0
    private var currentTopMovie = -1

    init {
        loadTopMovies()
        loadNextPage()
    }

    fun loadNextPage() {
        currentPage++
        getMoviesPageUseCase.execute(currentPage) { result ->
            result.movies?.let { loadedMovies ->
                val updatedMovies = moviesMutable.value.orEmpty() + loadedMovies
                moviesMutable.postValue(updatedMovies)
            }
        }
    }

    private fun loadTopMovies() {
        getTopMoviesUseCase.execute { movies ->
            topMoviesMutable.postValue(movies)
        }
    }

    fun startProgressBarCycle() {
        val topMoviesList = topMoviesMutable.value ?: return
        currentTopMovie = (currentTopMovie + 1) % topMoviesList.size
        val newState = List(topMoviesList.size) { index ->
            if (index == currentTopMovie) 100 else 0
        }
        progressBarStateMutable.value = newState
        startProgressBar()
    }

    private fun startProgressBar() {
        val timer = object : CountDownTimer(5000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                // Logic to update the progress of each bar could be added here
            }
            override fun onFinish() {
                startProgressBarCycle()
            }
        }
        timer.start()
    }

}