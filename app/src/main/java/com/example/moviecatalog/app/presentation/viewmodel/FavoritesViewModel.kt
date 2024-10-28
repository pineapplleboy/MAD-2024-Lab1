package com.example.moviecatalog.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.app.presentation.ui.compose.Favorites
import com.example.moviecatalog.domain.model.Genre
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.usecase.DeleteFavoriteGenreUseCase
import com.example.moviecatalog.domain.usecase.GetFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.GetFavoritesUseCase
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val getFavoriteGenresUseCase: GetFavoriteGenresUseCase,
    private val deleteFavoriteGenreUseCase: DeleteFavoriteGenreUseCase
) : ViewModel() {

    private val favoriteGenresMutable = MutableLiveData<List<Genre>>()
    val favoriteGenres: LiveData<List<Genre>> get() = favoriteGenresMutable


    private val favoriteMoviesMutable = MutableLiveData<List<MovieElement>>()
    val favoriteMovies: LiveData<List<MovieElement>> get() = favoriteMoviesMutable


    fun getFavoriteMovies() {
        viewModelScope.launch {

            val result = getFavoritesUseCase.execute()
            result.onSuccess {
                favoriteMoviesMutable.value = it
            }
        }
    }

    fun getFavoriteGenres() {
        favoriteGenresMutable.value = getFavoriteGenresUseCase.execute()
    }

    fun deleteFavoriteGenre(genre: Genre){
        deleteFavoriteGenreUseCase.execute(genre)
        getFavoriteGenres()
    }
}