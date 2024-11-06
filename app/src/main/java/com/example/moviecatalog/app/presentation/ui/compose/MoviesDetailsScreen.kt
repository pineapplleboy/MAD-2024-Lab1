package com.example.moviecatalog.app.presentation.ui.compose

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import kotlinx.coroutines.flow.map

@Composable
fun MoviesDetailsScreen(
    context: Context,
    vm: MovieDetailsViewModel,
    modifier: Modifier = Modifier
) {
    vm.isInFavoritesCheck()
    vm.getFriends()

    val movie by vm.movie.observeAsState()
    val friends by vm.friends.observeAsState()
    val addedToFavorites by vm.addedToFavorites.observeAsState()

    val lazyListState = rememberLazyListState()
    var isTitleVisible by remember { mutableStateOf(true) }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .map { index -> index > 1 }
            .collect { isTitleVisible = !it }
    }

    if (movie != null) {
        MoviePoster(
            link = movie!!.poster
        )

        Column(
            modifier = modifier.fillMaxSize()
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 76.dp,
                        bottom = 32.dp
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

                if(!isTitleVisible){
                    Spacer(
                        modifier = Modifier.width(16.dp)
                    )

                    Text(
                        text = movie!!.name ?: "",
                        color = colorResource(id = R.color.white),
                        maxLines = 1,
                        fontFamily = FontFamily(Font(R.font.manrope_bold)),
                        fontSize = 24.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    )

                    Spacer(
                        modifier = Modifier.width(16.dp)
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

            LazyColumn(
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        bottom = 24.dp
                    )
            ) {
                item{
                    Spacer(
                        modifier = Modifier.height((295.dp))
                    )
                }

                item{
                    ShortMovieInfo(
                        name = movie!!.name ?: stringResource(R.string.unnamed),
                        tagline = movie!!.tagline ?: ""
                    )
                }

                item{
                    FriendsInfo(
                        friends = friends ?: listOf()
                    )
                }

                item{
                    MovieDescription(
                        text = movie!!.description ?: ""
                    )
                }

                item{
                    MovieRating()
                }

                item{
                    InformationPanel(
                        countries = movie!!.country ?: "",
                        durability = movie!!.time,
                        age = movie!!.ageLimit,
                        year = movie!!.year
                    )
                }

                item{
                    DirectorPanel(name = movie!!.director ?: "", image = movie!!.director)
                }

                item{
                    GenresPanel(
                        genres = movie!!.genres ?: listOf(),
                        vm = vm
                    )
                }

                item{
                    FinancePanel(
                        budget = movie!!.budget ?: 0,
                        income = movie!!.fees ?: 0
                    )
                }

                item{
                    ReviewPanel(reviews = movie!!.reviews ?: listOf()){
                        vm.addFriend(it)
                    }
                }
            }
        }
    }
}