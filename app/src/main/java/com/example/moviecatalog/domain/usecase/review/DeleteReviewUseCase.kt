package com.example.moviecatalog.domain.usecase.review

import com.example.moviecatalog.domain.model.ReviewModify
import com.example.moviecatalog.domain.repository.ReviewRepository

class DeleteReviewUseCase(
    private val repository: ReviewRepository
) {
    suspend fun execute(movieId: String, id: String): Result<Unit> {
        return repository.deleteReview(movieId, id)
    }
}