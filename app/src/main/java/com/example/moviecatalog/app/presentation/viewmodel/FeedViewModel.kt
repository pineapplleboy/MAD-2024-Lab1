package com.example.moviecatalog.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.domain.model.Genre
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.usecase.favorites.AddFavoriteGenreUseCase
import com.example.moviecatalog.domain.usecase.favorites.AddToFavoritesUseCase
import com.example.moviecatalog.domain.usecase.favorites.GetFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.feed.GetNextUseCase
import kotlinx.coroutines.launch

class FeedViewModel(
    private val getNextUseCase: GetNextUseCase,
    private val addFavoriteUseCase: AddToFavoritesUseCase,
    private val getFavoriteGenresUseCase: GetFavoriteGenresUseCase
) : ViewModel() {

    private val moviesMutable = MutableLiveData<List<MovieElement>>(listOf())
    val movies: LiveData<List<MovieElement>> get() = moviesMutable


    private val favoriteGenresMutable = MutableLiveData<List<Genre>>(listOf())
    val favoriteGenres: LiveData<List<Genre>> get() = favoriteGenresMutable

    fun getNextMovie(currMovie: MovieElement?) {
        viewModelScope.launch {
            val movie = getNextUseCase.execute(currMovie)
            if (movie != null) {
                val currentMovies = moviesMutable.value ?: emptyList()
                moviesMutable.value = currentMovies + movie
            }
        }
    }

    fun addToFavorites(id: String) {
        viewModelScope.launch {
            addFavoriteUseCase.execute(id)
        }
    }

    fun getFavoriteGenres() {
        favoriteGenresMutable.value = getFavoriteGenresUseCase.execute()
    }
}