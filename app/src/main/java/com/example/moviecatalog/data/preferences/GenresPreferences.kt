package com.example.moviecatalog.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.moviecatalog.data.model.ApiGenre
import com.example.moviecatalog.domain.model.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenresPreferences(
    context: Context
) {

    companion object {
        private const val PREFS_NAME = "genres_prefs"
        private const val GENRES_TOKEN_KEY = "genres_token"
    }

    private val gson = Gson()

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getGenres(): List<Genre> {
        val json = sharedPreferences.getString(GENRES_TOKEN_KEY, null)
        return if (json.isNullOrEmpty()) {
            emptyList()
        } else {
            val type = object : TypeToken<List<Genre>>() {}.type
            gson.fromJson(json, type)
        }
    }

    fun addGenre(genre: Genre) {
        val genres = getGenres().toMutableList()
        genres.add(genre)
        saveGenres(genres)
    }

    fun deleteGenre(genre: Genre) {
        val genres = getGenres().toMutableList()
        genres.removeAll { it.id == genre.id }
        saveGenres(genres)
    }

    private fun saveGenres(genres: List<Genre>) {
        val editor = sharedPreferences.edit()
        editor.putString(GENRES_TOKEN_KEY, gson.toJson(genres))
        editor.apply()
    }

    fun clear() {
        val editor = sharedPreferences.edit()
        editor.putString(GENRES_TOKEN_KEY, gson.toJson(listOf<Genre>()))
        editor.apply()
    }
}