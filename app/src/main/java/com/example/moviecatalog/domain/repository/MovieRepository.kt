package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.domain.model.MovieDetails
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.model.MoviesPagedList

interface MovieRepository {

    suspend fun getMoviesByPage(page: Int): Result<MoviesPagedList>

    suspend fun getMovieDetails(id: String): Result<MovieDetails>
}