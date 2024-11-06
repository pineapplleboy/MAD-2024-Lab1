package com.example.moviecatalog.app.presentation.ui.compose

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberAsyncImagePainter
import com.example.moviecatalog.R
import com.example.moviecatalog.app.presentation.ui.activity.MovieDetailsActivity
import com.example.moviecatalog.app.presentation.viewmodel.FavoritesViewModel
import com.example.moviecatalog.domain.model.Genre
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.model.ReviewShort

@Composable
fun Favorites(
    vm: FavoritesViewModel,
    modifier: Modifier = Modifier
) {
    val favoriteMovies by vm.favoriteMovies.observeAsState(listOf())
    val favoriteGenres by vm.favoriteGenres.observeAsState(listOf())

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.dark))
            .padding(top = 76.dp, start = 24.dp, end = 24.dp)
    ) {
        item(
            span = { GridItemSpan(3)}
        ) {
            Text(
                text = stringResource(R.string.favorites),
                fontFamily = FontFamily(Font(R.font.manrope_bold)),
                color = colorResource(id = R.color.white),
                fontSize = 24.sp
            )
        }

        item(
            span = { GridItemSpan(3)}
        ) {
            Text(
                text = stringResource(R.string.favorite_genres),
                color = colorResource(id = R.color.white),
                fontFamily = FontFamily(Font(R.font.manrope_bold)),
                fontSize = 20.sp
            )
        }

        items(
            favoriteGenres,
            span = { GridItemSpan(3)}
        ) { genre ->
            GenreElement(
                genre = genre,
                onDelete = { vm.deleteFavoriteGenre(genre) }
            )
        }

        item(
            span = { GridItemSpan(3)}
        ) {
            Text(
                text = stringResource(R.string.favorite_movies),
                color = colorResource(id = R.color.white),
                fontFamily = FontFamily(Font(R.font.manrope_bold)),
                fontSize = 20.sp
            )
        }

        items(
            favoriteMovies
        ) { movie ->
            MovieElementUI(
                movie = movie,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
            )
        }
    }
}

//@Composable
//fun EmptyFavoritesScreen(
//    modifier: Modifier = Modifier
//){
//    Image(
//        painter = painterResource(id = R.drawable.welcome_screen_bg),
//        contentDescription = null,
//        contentScale = ContentScale.Crop,
//        modifier = Modifier.fillMaxSize()
//    )
//
//    Column(
//        modifier = Modifier.fillMaxSize()
//    ){
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(434.dp)
//                .background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            colorResource(id = R.color.shadow_dark),
//                            colorResource(id = R.color.shadow_bright)
//                        )
//                    )
//                )
//        )
//    }
//
//    Column(
//        modifier = Modifier.fillMaxSize()
//    ){
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(314.dp)
//                .background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            colorResource(id = R.color.shadow_bright),
//                            colorResource(id = R.color.shadow_dark)
//                        )
//                    )
//                )
//        )
//
//        Column(
//            modifier = Modifier
//                .background(
//                    color = colorResource(id = R.color.dark)
//                )
//                .fillMaxWidth()
//                .padding(
//                    start = 24.dp,
//                    end = 24.dp,
//                    bottom = 104.dp,
//                    top = 32.dp
//                )
//        ) {
//            Text(
//                text = stringResource(R.string.favorites_empty),
//                fontSize = 24.sp,
//                fontFamily = FontFamily(Font(R.font.manrope_bold)),
//                color = colorResource(id = R.color.white)
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = stringResource(R.string.empty_favorites_tip),
//                fontSize = 16.sp,
//                fontFamily = FontFamily(Font(R.font.manrope)),
//                color = colorResource(id = R.color.white)
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Text(
//                text = stringResource(R.string.find_movie_button_text),
//                fontSize = 14.sp,
//                fontFamily = FontFamily(Font(R.font.manrope_bold)),
//                color = colorResource(id = R.color.white),
//                modifier = Modifier
//                    .background(
//                        brush = Brush.linearGradient(
//                            colors = listOf(
//                                colorResource(id = R.color.orange_dark),
//                                colorResource(id = R.color.orange_bright)
//                            )
//                        ),
//                        shape = RoundedCornerShape(8.dp)
//                    )
//                    .padding(horizontal = 24.dp, vertical = 14.dp)
//            )
//        }
//    }
//
//}

@Composable
fun GenreElement(
    genre: Genre,
    modifier: Modifier = Modifier,
    onDelete: (Genre) -> Unit
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
                onDelete(genre)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.dislike),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp),
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
    val context = LocalContext.current

    Box(
        modifier = modifier
    ){
        Image(
            painter = rememberAsyncImagePainter(movie.poster),
            contentDescription = stringResource(id = R.string.movie_poster),
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    val intent = Intent(context, MovieDetailsActivity::class.java).apply {
                        putExtra("MOVIE_ID", movie.id)
                    }

                    context.startActivity(intent)
                }
        )

        Box(
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp)
                .background(
                    color = colorResource(id = R.color.black),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(
                    vertical = 4.dp,
                    horizontal = 8.dp
                )
        ){
            Text(
                text = "1.0",
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.manrope)),
                color = colorResource(id = R.color.white),
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}