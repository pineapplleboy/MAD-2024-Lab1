package com.example.moviecatalog.domain.usecase.movies

import com.example.moviecatalog.domain.model.MovieElement

class GetMovieRatingUseCase {

    fun execute(movie: MovieElement): String{
        return String.format("%.1f",
            movie.reviews?.sumOf { it.rating }?.toFloat()?.div(movie.reviews.size))
    }
}