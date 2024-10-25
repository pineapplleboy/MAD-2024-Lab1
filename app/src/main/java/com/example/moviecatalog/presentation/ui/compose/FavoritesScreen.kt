package com.example.moviecatalog.presentation.ui.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.R
import com.example.moviecatalog.domain.model.Genre
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.model.ReviewShort

@Preview
@Composable
fun FavoritesPreview() {
    Favorites(
        genres = listOf(
            Genre("", "Horror"),
            Genre("", "Drama"),
            Genre("", "Anime"),
            Genre("", "Action"),
        ),
        movies = listOf(
            MovieElement(
                id = "",
                name = "",
                country = "",
                year = 1313,
                poster = "",
                genres = listOf(),
                reviews = listOf()
            ),
            MovieElement(
                id = "",
                name = "",
                country = "",
                year = 1313,
                poster = "",
                genres = listOf(),
                reviews = listOf()
            ),
            MovieElement(
                id = "",
                name = "",
                country = "",
                year = 1313,
                poster = "",
                genres = listOf(),
                reviews = listOf()
            ),
            MovieElement(
                id = "",
                name = "",
                country = "",
                year = 1313,
                poster = "",
                genres = listOf(),
                reviews = listOf()
            )
        )
    )
}

@Composable
fun Favorites(
    genres: List<Genre>,
    movies: List<MovieElement>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier
            .background(
                color = colorResource(id = R.color.dark)
            )
            .padding(
                top = 76.dp,
                start = 24.dp,
                end = 24.dp
            )
    ){
        Text(
            text = stringResource(R.string.favorites),
            fontFamily = FontFamily(Font(R.font.manrope_bold)),
            color = colorResource(id = R.color.white),
            fontSize = 24.sp
        )
        FavoriteGenres(
            genres = genres
        )
        FavoriteMovies(
            movies = movies
        )
    }
}

@Composable
fun FavoriteGenres(
    genres: List<Genre>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.favorite_genres),
            color = colorResource(id = R.color.white),
            fontFamily = FontFamily(Font(R.font.manrope_bold)),
            fontSize = 20.sp
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(genres){
                GenreElement(genre = it)
            }
        }
    }
}

@Composable
fun FavoriteMovies(
    movies: List<MovieElement>,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.favorite_movies),
            color = colorResource(id = R.color.white),
            fontFamily = FontFamily(Font(R.font.manrope_bold)),
            fontSize = 20.sp
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(movies){
                MovieElementUI(movie = it)
            }
        }
    }
}

@Composable
fun GenreElement(
    genre: Genre,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.dark_faded),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 12.dp
            )
    ) {

        Text(
            text = genre.name ?: "",
            color = colorResource(id = R.color.white),
            fontFamily = FontFamily(Font(R.font.manrope)),
            fontSize = 20.sp
        )

        IconButton(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = colorResource(id = R.color.dark),
                    shape = RoundedCornerShape(8.dp)
                ),
            onClick = {
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.chevron_left),
                contentDescription = null,
                tint = colorResource(id = R.color.white)
            )
        }
    }
}

@Composable
fun MovieElementUI(
    movie: MovieElement,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
    ){
        Image(
            painter = painterResource(id = R.drawable.test_movie_poster),
            contentDescription = stringResource(id = R.string.movie_poster),
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
        )
    }
}