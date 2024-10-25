package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.MovieCatalogApi
import com.example.moviecatalog.data.model.ApiGenre
import com.example.moviecatalog.data.model.ApiMovieDetails
import com.example.moviecatalog.data.model.ApiMovieElement
import com.example.moviecatalog.data.model.ApiReviewShort
import com.example.moviecatalog.data.safeApiCall
import com.example.moviecatalog.domain.model.Genre
import com.example.moviecatalog.domain.model.MovieDetails
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.model.MoviesPagedList
import com.example.moviecatalog.domain.model.PageInfo
import com.example.moviecatalog.domain.model.ReviewShort
import com.example.moviecatalog.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val api: MovieCatalogApi
): MovieRepository {

    override suspend fun getMoviesByPage(page: Int): Result<MoviesPagedList> {

        return safeApiCall(
            apiCall = {
                api.getMoviesByPage(page).execute()
            },
            transform = {
                MoviesPagedList(
                    pageInfo = PageInfo(
                        pageCount = it.pageInfo.pageCount,
                        pageSize = it.pageInfo.pageSize,
                        currentPage = it.pageInfo.currentPage
                    ),
                    movies = it.movies?.toDomainModelList()
                )
            }
        )
    }

    override suspend fun getMovieDetails(id: String): Result<MovieDetails> {

        return safeApiCall(
            apiCall = {
                api.getMovieDetails(id).execute()
            },
            transform = {
                it.toDomainModel()
            }
        )
    }
}

fun ApiMovieDetails.toDomainModel(): MovieDetails{
    return MovieDetails(
        name = this.name,
        id = this.id,
        poster = this.poster,
        year = this.year,
        country = this.country,
        genres = this.genres?.map{ it.toDomainModel() },
        reviews = this.reviews?.map {it.toDomainModel() },
        time = this.time,
        tagline = this.tagline,
        description = this.description,
        director = this.director,
        budget = this.budget,
        ageLimit = this.ageLimit,
        fees = this.fees
    )
}

fun ApiMovieElement.toDomainModel(): MovieElement {
    return MovieElement(
        id = this.id,
        name = this.name,
        poster = this.poster,
        year = this.year,
        country = this.country,
        genres = this.genres?.map { it.toDomainModel() },
        reviews = this.reviews?.map { it.toDomainModel() }
    )
}

fun ApiGenre.toDomainModel() : Genre {
    return Genre(
        id = this.id,
        name = this.name
    )
}

fun ApiReviewShort.toDomainModel(): ReviewShort{
    return ReviewShort(
        id = this.id,
        rating = this.rating
    )
}

fun List<ApiMovieElement>.toDomainModelList(): List<MovieElement> {
    return this.map { it.toDomainModel() }
}