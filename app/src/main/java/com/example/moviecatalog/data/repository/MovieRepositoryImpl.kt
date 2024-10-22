package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.MovieApi
import com.example.moviecatalog.data.model.ApiGenre
import com.example.moviecatalog.data.model.ApiMovieDetails
import com.example.moviecatalog.data.model.ApiMovieElement
import com.example.moviecatalog.data.model.ApiMoviesPagedList
import com.example.moviecatalog.data.model.ApiReviewShort
import com.example.moviecatalog.data.model.ApiUserProfile
import com.example.moviecatalog.domain.model.Genre
import com.example.moviecatalog.domain.model.MovieDetails
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.model.MoviesPagedList
import com.example.moviecatalog.domain.model.PageInfo
import com.example.moviecatalog.domain.model.ReviewShort
import com.example.moviecatalog.domain.repository.MovieRepository
import retrofit2.Call
import retrofit2.Response

class MovieRepositoryImpl(
    private val movieApi: MovieApi
): MovieRepository {

    override fun getMoviesByPage(page: Int, callback: (Result<MoviesPagedList>) -> Unit) {
        movieApi.getMoviesByPage(page).enqueue(object : retrofit2.Callback<ApiMoviesPagedList>{

            override fun onResponse(
                call: Call<ApiMoviesPagedList>,
                response: Response<ApiMoviesPagedList>
            ) {

                if(response.isSuccessful){

                    val movies = response.body()
                    if(movies != null){
                        callback(Result.success(
                            MoviesPagedList(
                                pageInfo = PageInfo(
                                    pageCount = movies.pageInfo.pageCount,
                                    pageSize = movies.pageInfo.pageSize,
                                    currentPage = movies.pageInfo.currentPage
                                ),
                                movies = movies.movies?.toDomainModelList()
                            )
                        ))
                    }
                }

                else{
                    val errorBody = response.errorBody()?.string()
                    callback(Result.failure(Exception("Getting page failed: $errorBody")))
                }
            }

            override fun onFailure(call: Call<ApiMoviesPagedList>, t: Throwable) {
                callback(Result.failure(Exception("Network error: ${t.message}")))
            }
        })
    }

    override fun getMovieDetails(id: String, callback: (Result<MovieDetails>) -> Unit) {
        movieApi.getMovieDetails(id).enqueue(object : retrofit2.Callback<ApiMovieDetails>{

            override fun onResponse(
                call: Call<ApiMovieDetails>,
                response: Response<ApiMovieDetails>
            ) {
                if(response.isSuccessful){

                    val movie = response.body()
                    if(movie != null){
                        callback(Result.success(
                            movie.toDomainModel()
                        ))
                    }
                }

                else{
                    val errorBody = response.errorBody()?.string()
                    callback(Result.failure(Exception("Getting movie details failed: $errorBody")))
                }
            }

            override fun onFailure(call: Call<ApiMovieDetails>, t: Throwable) {
                callback(Result.failure(Exception("Network error: ${t.message}")))
            }
        })
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
}