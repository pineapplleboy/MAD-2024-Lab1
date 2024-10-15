package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.model.MoviesPagedList

interface MovieRepository {

    fun getMoviesByPage(page: Int, callback: (Result<MoviesPagedList>) -> Unit)
}