package com.example.moviecatalog.domain.usecase.review

import com.example.moviecatalog.domain.model.ReviewModify
import com.example.moviecatalog.domain.repository.ReviewRepository

class AddReviewUseCase(
    private val repository: ReviewRepository
) {
    suspend fun execute(review: ReviewModify, movieId: String): Result<Unit> {
        return repository.addReview(review, movieId)
    }
}