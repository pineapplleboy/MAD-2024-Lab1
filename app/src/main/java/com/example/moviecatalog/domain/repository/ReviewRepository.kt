package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.domain.model.Review
import com.example.moviecatalog.domain.model.ReviewModify

interface ReviewRepository {

    suspend fun addReview(review: ReviewModify, movieId: String): Result<Unit>

    suspend fun editReview(movieId: String, id: String, review: ReviewModify): Result<Unit>

    suspend fun deleteReview(movieId: String, id: String): Result<Unit>
}