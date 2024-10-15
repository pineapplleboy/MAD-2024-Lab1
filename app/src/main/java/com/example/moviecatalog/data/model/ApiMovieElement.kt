package com.example.moviecatalog.data.model

class ApiMovieElement(
    val id: String,
    val name: String?,
    val poster: String?,
    val year: Int,
    val country: String?,
    val genres: List<ApiGenre>?,
    val reviews: List<ApiReviewShort>?
)