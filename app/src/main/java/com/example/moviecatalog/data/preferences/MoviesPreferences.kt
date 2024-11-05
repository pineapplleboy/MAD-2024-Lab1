package com.example.moviecatalog.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.model.MoviesPagedList
import com.example.moviecatalog.domain.model.PageInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MoviesPreferences(
    context: Context
) {

    companion object {
        private const val PREFS_NAME = "movies_prefs"
        private const val REMAINING_MOVIES_TOKEN_KEY = "remaining_movies_token"
        private const val PAGE_INFO_KEY = "page_info_token"
    }

    private val gson = Gson()

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    init{
        val movies = listOf<MovieElement>()
        saveMovies(movies)

        val pageInfo: PageInfo? = null
        val editor = sharedPreferences.edit()
        editor.putString(PAGE_INFO_KEY, gson.toJson(pageInfo))
        editor.apply()
    }

    fun getRemainingMovies(): List<MovieElement> {
        val json = sharedPreferences.getString(REMAINING_MOVIES_TOKEN_KEY, null)
        return if (json.isNullOrEmpty()) {
            emptyList()
        } else {
            val type = object : TypeToken<List<MovieElement>>() {}.type
            gson.fromJson(json, type)
        }
    }

    fun skipMovie(movie: MovieElement) {
        val movies = getRemainingMovies().toMutableList()
        movies.remove(movies.find {movie.id == it.id})
        saveMovies(movies)
    }

    fun changePage(moviesPagedInfo: MoviesPagedList){
        val editor = sharedPreferences.edit()
        editor.putString(PAGE_INFO_KEY, gson.toJson(moviesPagedInfo.pageInfo))
        editor.apply()

        val movies: List<MovieElement> = moviesPagedInfo.movies ?: listOf()
        saveMovies(movies)
    }

    fun getPageInfo(): PageInfo? {
        val json = sharedPreferences.getString(PAGE_INFO_KEY, null)
        return if (json.isNullOrEmpty()) {
            null
        } else {
            val type = object : TypeToken<PageInfo>() {}.type
            gson.fromJson(json, type)
        }
    }

    private fun saveMovies(movies: List<MovieElement>) {
        val editor = sharedPreferences.edit()
        editor.putString(REMAINING_MOVIES_TOKEN_KEY, gson.toJson(movies))
        editor.apply()
    }
}