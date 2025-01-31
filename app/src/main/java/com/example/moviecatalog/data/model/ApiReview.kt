package com.example.moviecatalog.data.model

class ApiReview(
    val id: String,
    val rating: Int,
    val reviewText: String?,
    val isAnonymous: Boolean,
    val createDateTime: String,
    val author: ApiUserShort
)