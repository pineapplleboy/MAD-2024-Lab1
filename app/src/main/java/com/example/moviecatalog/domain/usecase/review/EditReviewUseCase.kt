package com.example.moviecatalog.domain.usecase.review

import com.example.moviecatalog.domain.model.ReviewModify
import com.example.moviecatalog.domain.repository.ReviewRepository

class EditReviewUseCase(
    private val repository: ReviewRepository
) {
    suspend fun execute(review: ReviewModify, movieId: String, id: String): Result<Unit> {
        return repository.editReview(movieId, id, review)
    }
}