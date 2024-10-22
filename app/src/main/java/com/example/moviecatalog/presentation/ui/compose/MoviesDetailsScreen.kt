package com.example.moviecatalog.presentation.ui.compose

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.moviecatalog.R
import com.example.moviecatalog.domain.model.MovieDetails
import com.example.moviecatalog.presentation.ui.activity.MoviesActivity

@Composable
fun MoviesDetails(
    movie: MovieDetails,
    context: Context,
    modifier: Modifier = Modifier
) {
    MoviePoster(
        link = movie.poster
    )

    LazyColumn {
        item{
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 411.dp,
                        bottom = 24.dp
                    )
            ) {
                ShortMovieInfo(
                    name = movie.name ?: stringResource(R.string.unnamed),
                    tagline = movie.tagline ?: ""
                )
                FriendsInfo(
                    friends = listOf()
                )
                MovieDescription(
                    text = movie.description ?: ""
                )
                MovieRating()
                InformationPanel(
                    countries = movie.country ?: "",
                    durability = movie.time,
                    age = movie.ageLimit,
                    year = movie.year
                )
                DirectorPanel(name = movie.director ?: "", image = "")
                GenresPanel(genres = movie.genres?.map{ genre -> genre.name ?: "" } ?: listOf())
                FinancePanel(
                    budget = movie.budget ?: 0,
                    income = movie.fees ?: 0
                )
            }
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(
                horizontal = 24.dp,
                vertical = 76.dp
            )
    ){
        IconButton(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = colorResource(id = R.color.dark_faded),
                    shape = RoundedCornerShape(8.dp)
                ),
            onClick = {
                val intent = Intent(context, MoviesActivity::class.java)
                context.startActivity(intent)
            }) {
            Image(
                painter = painterResource(id = R.drawable.chevron_left),
                colorFilter = ColorFilter.tint(colorResource(id = R.color.gray)),
                contentDescription = stringResource(R.string.return_to_movies_screen)
            )
        }

        IconButton(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = colorResource(id = R.color.dark_faded),
                    shape = RoundedCornerShape(8.dp)
                ),
            onClick = {

            }) {
            Image(
                painter = painterResource(id = R.drawable.like),
                colorFilter = ColorFilter.tint(colorResource(id = R.color.gray)),
                contentDescription = stringResource(R.string.return_to_movies_screen)
            )
        }
    }
}

@Composable
fun MoviePoster(
    link: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(412.dp)
            .height(464.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(link),
            contentDescription = stringResource(R.string.movie_poster),
            modifier = Modifier
                .width(412.dp)
                .height(464.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.matchParentSize()
        ){
            Spacer(modifier = Modifier.height(304.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorResource(id = R.color.shadow_bright),
                                colorResource(id = R.color.shadow_dark)
                            )
                        )
                    )
            )
        }
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
    MoviePanel(
        iconID = R.drawable.star,
        textID = R.string.rating,
        modifier = modifier
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ){

            RatingScore(
                value = 9.9f,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ){
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(R.string.movie_catalog_rating),
                    modifier = Modifier
                        .width(40.dp)
                        .height(21.08.dp)
                )
            }

            RatingScore(
                value = 0.6f,
                modifier = Modifier.wrapContentWidth()
            ){
                Image(
                    painter = painterResource(id = R.drawable.kinopoisk_logo),
                    contentDescription = stringResource(R.string.kinopoisk_rating),
                    modifier = Modifier
                        .width(22.63.dp)
                        .height(24.dp)
                )
            }

            RatingScore(
                value = 2.8f,
                modifier = Modifier.wrapContentWidth()
            ){
                Image(
                    painter = painterResource(id = R.drawable.imdb_logo),
                    contentDescription = stringResource(R.string.imdb_rating),
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
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

@Composable
fun MoviePanel(
    modifier: Modifier = Modifier,
    iconID: Int,
    textID: Int,
    content: @Composable () -> Unit
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
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                painter = painterResource(id = iconID),
                colorFilter = ColorFilter.tint(colorResource(id = R.color.gray)),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
            )
            Text(
                text = stringResource(textID),
                color = colorResource(id = R.color.gray),
                fontSize = 16.sp,
                modifier = Modifier.wrapContentSize()
            )
        }

        content()
    }
}

@Composable
fun InformationPanel(
    countries: String,
    age: Int,
    durability: Int,
    year: Int,
    modifier: Modifier = Modifier
){
    MoviePanel(
        iconID = R.drawable.info,
        textID = R.string.information_panel
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InformationPanelElement(
                    name = stringResource(R.string.information_countries),
                    content = countries,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                InformationPanelElement(
                    name = stringResource(R.string.information_age),
                    content = "${age}+",
                    modifier = Modifier.wrapContentWidth()
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                InformationPanelElement(
                    name = stringResource(R.string.information_durability),
                    content = durability.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                InformationPanelElement(
                    name = stringResource(R.string.information_release_year),
                    content = year.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}

@Composable
fun InformationPanelElement(
    modifier: Modifier = Modifier,
    name: String,
    content: String
){
    Column(
        modifier = modifier
            .background(
                color = colorResource(id = R.color.dark),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
    ){
        Text(
            text = name,
            color = colorResource(id = R.color.gray),
            fontFamily = FontFamily(Font(R.font.manrope)),
            fontSize = 14.sp
        )
        Text(
            text = content,
            color = colorResource(id = R.color.white),
            fontFamily = FontFamily(Font(R.font.manrope)),
            fontSize = 16.sp
        )
    }
}

@Composable
fun DirectorPanel(
    name: String,
    image: String,
    modifier: Modifier = Modifier
){
    MoviePanel(
        iconID = R.drawable.money,
        textID = R.string.information_director,
        modifier = modifier
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.dark),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                )
        ){
            Image(
                painter = painterResource(id = R.drawable.default_profile_icon),
                contentDescription = stringResource(R.string.director_photo_description),
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = name,
                color = colorResource(id = R.color.white),
                fontFamily = FontFamily(Font(R.font.manrope)),
                fontSize = 16.sp
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenresPanel(
    genres: List<String>,
    modifier: Modifier = Modifier
){
    MoviePanel(
        iconID = R.drawable.genres,
        textID = R.string.information_genres
    ) {
        FlowRow(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            genres.forEach { genre ->
                Genre(
                    name = genre
                )
            }
        }
    }
}

@Composable
fun Genre(
    name: String,
    modifier: Modifier = Modifier
){
    Text(
        text = name,
        color = colorResource(id = R.color.white),
        textAlign = TextAlign.Center,
        modifier = modifier
            .background(
                color = colorResource(id = R.color.dark),
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            ),
        fontFamily = FontFamily(Font(R.font.manrope)),
        fontSize = 16.sp
    )
}

@Composable
fun FinancePanel(
    budget: Int,
    income: Int,
    modifier: Modifier = Modifier
){
    MoviePanel(
        iconID = R.drawable.money,
        textID = R.string.information_finance,
        modifier = modifier) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            InformationPanelElement(
                name = stringResource(R.string.information_budget),
                content = budget.toString(),
                modifier = Modifier.weight(1f)
            )
            InformationPanelElement(
                name = stringResource(R.string.information_income),
                content = income.toString(),
                modifier = Modifier.weight(1f)
            )
        }
    }
}