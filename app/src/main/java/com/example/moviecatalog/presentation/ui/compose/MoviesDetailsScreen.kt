package com.example.moviecatalog.presentation.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.R

@Preview
@Composable
fun MoviesDetails(modifier: Modifier = Modifier) {
    MoviePoster(link = "https://cdn.ananasposter.ru/image/cache/catalog/poster/anime/81/17497-1000x830.jpg")

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 411.dp,
                bottom = 24.dp
            )
    ) {
        ShortMovieInfo(
            name = "Мега фильм",
            tagline = "What is lost will be found"
        )
        FriendsInfo(
            friends = listOf()
        )
        MovieDescription(
            text = "Группа европейских мигрантов покидает Лондон на пароходе, чтобы начать новую жизнь в Нью-Йорке. Когда они сталкиваются с другим судном, плывущим по течению в открытом море, их путешествие превращается в кошмар"
        )
        MovieRating()
    }
}

@Composable
fun MoviePoster(
    link: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(412.dp)
            .height(464.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.test_movie_poster),
            contentDescription = stringResource(R.string.movie_poster),
            modifier = Modifier
                .width(412.dp)
                .height(464.dp),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .width(412.dp)
                .height(160.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(id = R.color.shadow_bright),
                            colorResource(id = R.color.shadow_dark)
                        )
                    )
                )
                .align(Alignment.BottomCenter)
        )
    }
}


@Composable
fun ShortMovieInfo(
    name: String,
    tagline: String,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        colorResource(id = R.color.orange_dark),
                        colorResource(id = R.color.orange_bright)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = name,
            color = colorResource(id = R.color.white),
            fontFamily = FontFamily(Font(R.font.manrope_bold)),
            fontSize = 36.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        Text(
            text = tagline,
            color = colorResource(id = R.color.white),
            fontFamily = FontFamily(Font(R.font.manrope)),
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}

@Composable
fun FriendsInfo(
    friends: List<Int>,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = colorResource(id = R.color.dark_faded),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ){

        Image(
            painter = painterResource(id = R.drawable.default_profile_icon),
            contentDescription = stringResource(R.string.friend_avatar),
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.default_profile_icon),
            contentDescription = stringResource(R.string.friend_avatar),
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.default_profile_icon),
            contentDescription = stringResource(R.string.friend_avatar),
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
        )

        Text(
            text = stringResource(R.string.friends_amount, friends.size),
            color = colorResource(id = R.color.white),
            fontFamily = FontFamily(Font(R.font.manrope)),
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}

@Composable
fun MovieDescription(
    text: String,
    modifier: Modifier = Modifier
){
    Text(
        text = text,
        color = colorResource(id = R.color.white),
        fontFamily = FontFamily(Font(R.font.manrope)),
        fontSize = 16.sp,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = colorResource(id = R.color.dark_faded),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    )
}

@Composable
fun MovieRating(
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = colorResource(id = R.color.dark_faded),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ){
            Image(
                painter = painterResource(id = R.drawable.star),
                colorFilter = ColorFilter.tint(colorResource(id = R.color.gray)),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
            )
            Text(
                text = stringResource(R.string.rating),
                color = colorResource(id = R.color.gray),
                fontSize = 16.sp,
                modifier = Modifier.wrapContentSize()
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ){

            RatingScore(
                value = 9.9f,
                modifier = Modifier.fillMaxWidth().weight(1f)
            ){
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(R.string.movie_catalog_rating),
                    modifier = Modifier.width(40.dp).height(21.08.dp)
                )
            }

            RatingScore(
                value = 0.6f,
                modifier = Modifier.wrapContentWidth()
            ){
                Image(
                    painter = painterResource(id = R.drawable.kinopoisk_logo),
                    contentDescription = stringResource(R.string.kinopoisk_rating),
                    modifier = Modifier.width(22.63.dp).height(24.dp)
                )
            }

            RatingScore(
                value = 2.8f,
                modifier = Modifier.wrapContentWidth()
            ){
                Image(
                    painter = painterResource(id = R.drawable.imdb_logo),
                    contentDescription = stringResource(R.string.imdb_rating),
                    modifier = Modifier.width(24.dp).height(24.dp)
                )
            }
        }
    }
}

@Composable
fun RatingScore(
    value: Float,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .wrapContentHeight()
            .background(
                color = colorResource(id = R.color.dark),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(
                start = 12.dp,
                end = 12.dp,
                top = 8.dp,
                bottom = 8.dp
            )
    ){

        icon()

        Text(
            text = value.toString(),
            color = colorResource(id = R.color.white),
            fontFamily = FontFamily(Font(R.font.manrope_bold)),
            fontSize = 20.sp
        )
    }
}

