package com.example.moviecatalog.app.presentation.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.domain.model.Genre
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.usecase.favorites.GetFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.movies.GetMoviesPageUseCase
import com.example.moviecatalog.domain.usecase.movies.GetTopMoviesUseCase
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val getMoviesPageUseCase: GetMoviesPageUseCase,
    private val getTopMoviesUseCase: GetTopMoviesUseCase,
    private val getFavoriteGenresUseCase: GetFavoriteGenresUseCase
) : ViewModel() {

    private val topMoviesMutable = MutableLiveData<List<MovieElement>>()
    val topMovies: LiveData<List<MovieElement>> get() = topMoviesMutable

    private val moviesMutable = MutableLiveData<List<MovieElement>>()
    val movies: LiveData<List<MovieElement>> get() = moviesMutable

    private val progressBarStateMutable = MutableLiveData<List<Int>>()

    private var currentPage = 0
    private var currentTopMovie = -1


    private val favoriteGenresMutable = MutableLiveData<List<Genre>>(listOf())
    val favoriteGenres: LiveData<List<Genre>> get() = favoriteGenresMutable

    init {
        loadTopMovies()
        loadNextPage()
    }

    fun loadNextPage() {
        viewModelScope.launch {
            currentPage++
            val result = getMoviesPageUseCase.execute(currentPage)
            result.onSuccess {
                val updatedMovies =
                    moviesMutable.value.orEmpty() + it.movies.orEmpty().filter { movie ->
                        topMovies.value?.find { topMovie ->
                            topMovie.id == movie.id
                        } == null
                    }
                moviesMutable.postValue(updatedMovies)
            }

        }
    }

    private fun loadTopMovies() {
        viewModelScope.launch {
            val result = getTopMoviesUseCase.execute()
            result.onSuccess {
                topMoviesMutable.postValue(it)
            }
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

            }

            override fun onFinish() {
                startProgressBarCycle()
            }
        }
        timer.start()
    }

    fun getFavoriteGenres() {
        favoriteGenresMutable.value = getFavoriteGenresUseCase.execute()
    }

}