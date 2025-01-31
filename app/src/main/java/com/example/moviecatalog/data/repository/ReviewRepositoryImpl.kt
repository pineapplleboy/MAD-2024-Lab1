package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.MovieCatalogApi
import com.example.moviecatalog.data.model.ApiReviewModify
import com.example.moviecatalog.data.safeApiCall
import com.example.moviecatalog.domain.model.ReviewModify
import com.example.moviecatalog.domain.repository.ReviewRepository

class ReviewRepositoryImpl(
    private val api: MovieCatalogApi
) : ReviewRepository {

    override suspend fun addReview(review: ReviewModify, movieId: String): Result<Unit> {
        return safeApiCall(
            apiCall = {
                api.addReview(
                    movieId,
                    ApiReviewModify(
                        reviewText = review.reviewText,
                        rating = review.rating,
                        isAnonymous = review.isAnonymous
                    )
                ).execute()
            },
            transform = {}
        )
    }

    override suspend fun editReview(
        movieId: String,
        id: String,
        review: ReviewModify
    ): Result<Unit> {
        return safeApiCall(
            apiCall = {
                api.editReview(
                    movieId = movieId,
                    id = id,
                    ApiReviewModify(
                        reviewText = review.reviewText,
                        rating = review.rating,
                        isAnonymous = review.isAnonymous
                    )
                ).execute()
            },
            transform = {}
        )
    }

    override suspend fun deleteReview(movieId: String, id: String): Result<Unit> {
        return safeApiCall(
            apiCall = {
                api.deleteReview(
                    movieId = movieId,
                    id = id
                ).execute()
            },
            transform = {}
        )
    }
}