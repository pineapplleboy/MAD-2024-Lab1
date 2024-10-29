package com.example.moviecatalog.app.presentation.ui.compose

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R
import com.example.moviecatalog.app.presentation.ui.activity.MainActivity
import com.example.moviecatalog.app.presentation.ui.compose.components.DirectorPanel
import com.example.moviecatalog.app.presentation.ui.compose.components.FinancePanel
import com.example.moviecatalog.app.presentation.ui.compose.components.FriendsInfo
import com.example.moviecatalog.app.presentation.ui.compose.components.GenresPanel
import com.example.moviecatalog.app.presentation.ui.compose.components.InformationPanel
import com.example.moviecatalog.app.presentation.ui.compose.components.MovieDescription
import com.example.moviecatalog.app.presentation.ui.compose.components.MoviePoster
import com.example.moviecatalog.app.presentation.ui.compose.components.MovieRating
import com.example.moviecatalog.app.presentation.ui.compose.components.ReviewPanel
import com.example.moviecatalog.app.presentation.ui.compose.components.ShortMovieInfo
import com.example.moviecatalog.app.presentation.viewmodel.MovieDetailsViewModel

@Composable
fun MoviesDetailsScreen(
    context: Context,
    vm: MovieDetailsViewModel,
    modifier: Modifier = Modifier
) {
    vm.isInFavorites()
    val movie by vm.movie.observeAsState()
    val addedToFavorites by vm.addedToFavorites.observeAsState()

    if (movie != null) {
        MoviePoster(
            link = movie!!.poster
        )

        LazyColumn {
            item {
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
                        name = movie!!.name ?: stringResource(R.string.unnamed),
                        tagline = movie!!.tagline ?: ""
                    )
                    FriendsInfo(
                        friends = listOf()
                    )
                    MovieDescription(
                        text = movie!!.description ?: ""
                    )
                    MovieRating()
                    InformationPanel(
                        countries = movie!!.country ?: "",
                        durability = movie!!.time,
                        age = movie!!.ageLimit,
                        year = movie!!.year
                    )
                    DirectorPanel(name = movie!!.director ?: "", image = movie!!.director)
                    GenresPanel(
                        genres = movie!!.genres ?: listOf(),
                        vm = vm
                    )
                    FinancePanel(
                        budget = movie!!.budget ?: 0,
                        income = movie!!.fees ?: 0
                    )
                    ReviewPanel(reviews = movie!!.reviews ?: listOf()){
                        vm.addFriend(it)
                    }
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
        ) {
            IconButton(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = colorResource(id = R.color.dark_faded),
                        shape = RoundedCornerShape(8.dp)
                    ),

                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }) {
                Image(
                    painter = painterResource(id = R.drawable.chevron_left),
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.gray)),
                    contentDescription = stringResource(R.string.return_to_movies_screen)
                )
            }

            IconButton(
                modifier = if (addedToFavorites == false) {

                    Modifier
                        .size(40.dp)
                        .background(
                            color = colorResource(id = R.color.dark_faded),
                            shape = RoundedCornerShape(8.dp)
                        )
                } else {

                    Modifier
                        .size(40.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    colorResource(id = R.color.orange_dark),
                                    colorResource(id = R.color.orange_bright)
                                )
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )

                },
                onClick = {
                    if (addedToFavorites == true) {
                        vm.deleteFromFavorites()
                    } else {
                        vm.addToFavorites()
                    }
                }) {

                Image(
                    painter = painterResource(
                        id = if (addedToFavorites == false)
                            R.drawable.like
                        else R.drawable.liked
                    ),
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.gray)),
                    contentDescription = stringResource(R.string.add_movie_to_favorites_button)
                )
            }
        }
    }
}