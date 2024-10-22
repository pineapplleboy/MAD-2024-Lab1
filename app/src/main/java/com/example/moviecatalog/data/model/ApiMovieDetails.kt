package com.example.moviecatalog.data.model

class ApiMovieDetails(
    val id: String,
    val name: String?,
    val poster: String?,
    val year: Int,
    val country: String?,
    val genres: List<ApiGenre>?,
    val reviews: List<ApiReviewShort>?,
    val time: Int,
    val tagline: String?,
    val description: String?,
    val director: String?,
    val budget: Int?,
    val fees: Int?,
    val ageLimit: Int
)