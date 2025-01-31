package com.example.moviecatalog.domain.model

class MoviesPagedList(
    val movies: List<MovieElement>?,
    val pageInfo: PageInfo
)