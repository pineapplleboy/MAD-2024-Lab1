package com.example.moviecatalog.app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.domain.model.Genre
import com.example.moviecatalog.domain.model.MovieDetails
import com.example.moviecatalog.domain.model.UserShort
import com.example.moviecatalog.domain.usecase.favorites.AddFavoriteGenreUseCase
import com.example.moviecatalog.domain.usecase.favorites.AddToFavoritesUseCase
import com.example.moviecatalog.domain.usecase.favorites.DeleteFavoriteGenreUseCase
import com.example.moviecatalog.domain.usecase.favorites.DeleteFromFavoritesUseCase
import com.example.moviecatalog.domain.usecase.favorites.GetFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.favorites.GetFavoritesUseCase
import com.example.moviecatalog.domain.usecase.friends.AddFriendUseCase
import com.example.moviecatalog.domain.usecase.friends.GetFriendsUseCase
import com.example.moviecatalog.domain.usecase.movies.GetMovieDetailsUseCase
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase,
    private val getFavoriteGenresUseCase: GetFavoriteGenresUseCase,
    private val addFavoriteGenreUseCase: AddFavoriteGenreUseCase,
    private val deleteFavoriteGenreUseCase: DeleteFavoriteGenreUseCase,
    private val addFriendUseCase: AddFriendUseCase,
    private val getFriendsUseCase: GetFriendsUseCase
) : ViewModel() {

    private val movieMutable = MutableLiveData<MovieDetails>()
    val movie: LiveData<MovieDetails> get() = movieMutable


    private val addedToFavoritesMutable = MutableLiveData(false)
    val addedToFavorites: LiveData<Boolean> get() = addedToFavoritesMutable


    private val favoriteGenresMutable = MutableLiveData<List<Genre>>()
    val favoriteGenres: LiveData<List<Genre>> get() = favoriteGenresMutable


    private val friendsMutable = MutableLiveData<List<UserShort>>()
    val friends: LiveData<List<UserShort>> get() = friendsMutable


    fun getFriends(){

        friendsMutable.value = getFriendsUseCase.execute().filter {friend ->
            (movie.value?.reviews?.find { friend.userId == it.author.userId }?.rating ?: 0) > 6
        }
    }


    fun getMovieDetails(id: String) {
        viewModelScope.launch {
            val result = getMovieDetailsUseCase.execute(id)
            result.onSuccess {
                movieMutable.value = it
            }
        }

        isInFavorites()
        getFavoriteGenres()
    }

    fun addToFavorites() {
        viewModelScope.launch {
            val result = movieMutable.value?.let { addToFavoritesUseCase.execute(it.id) }

            result?.onSuccess {
                addedToFavoritesMutable.value = true
            }
            result?.onFailure {
                Log.d("API", it.toString())
            }
        }
    }

    fun deleteFromFavorites() {
        viewModelScope.launch {
            val result = movieMutable.value?.let { deleteFromFavoritesUseCase.execute(it.id) }

            result?.onSuccess {
                addedToFavoritesMutable.value = false
            }
            result?.onFailure {
                Log.d("API", it.toString())
            }

        }
    }

    fun isInFavorites() {
        viewModelScope.launch {
            val result = getFavoritesUseCase.execute()

            result.onSuccess { favorites ->
                addedToFavoritesMutable.value = favorites.any { it.id == (movie.value?.id ?: "-1") }
            }
        }
    }

    private fun getFavoriteGenres() {
        favoriteGenresMutable.value = getFavoriteGenresUseCase.execute()
    }

    fun addToFavoriteGenres(genre: Genre) {
        addFavoriteGenreUseCase.execute(genre)
        getFavoriteGenres()
    }

    fun deleteFromFavoriteGenres(genre: Genre) {
        favoriteGenresMutable.value?.find { it.id == genre.id }
            ?.let { deleteFavoriteGenreUseCase.execute(it) }
        getFavoriteGenres()
    }

    fun checkFavoriteGenre(genre: Genre): Boolean {
        return favoriteGenres.value?.any { it.id == genre.id } ?: false
    }

    fun addFriend(user: UserShort){
        addFriendUseCase.execute(user)
        getFriends()
    }
}