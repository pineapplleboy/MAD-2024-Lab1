package com.example.moviecatalog.domain.model

data class MovieElement(
    val id: String,
    val name: String?,
    val poster: String?,
    val year: Int,
    val country: String?,
    val genres: List<Genre>?,
    val reviews: List<ReviewShort>?
)