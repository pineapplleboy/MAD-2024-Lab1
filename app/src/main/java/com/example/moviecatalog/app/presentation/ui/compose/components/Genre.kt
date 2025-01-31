package com.example.moviecatalog.app.presentation.ui.compose.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.R
import com.example.moviecatalog.app.presentation.viewmodel.MovieDetailsViewModel
import com.example.moviecatalog.domain.model.Genre

@Composable
fun Genre(
    genre: Genre,
    vm: MovieDetailsViewModel,
    modifier: Modifier = Modifier
) {
    val favoriteGenres by vm.favoriteGenres.observeAsState()

    val genreModifier = if (favoriteGenres?.any { it.id == genre.id } == true)
        modifier.background(
            brush = Brush.linearGradient(
                colors = listOf(
                    colorResource(id = R.color.orange_dark),
                    colorResource(id = R.color.orange_bright)
                )
            ),
            shape = RoundedCornerShape(8.dp),
        )
    else modifier.background(
        color = colorResource(id = R.color.dark),
        shape = RoundedCornerShape(8.dp),
    )

    Text(
        text = genre.name ?: "",
        color = colorResource(id = R.color.white),
        textAlign = TextAlign.Center,
        modifier = genreModifier
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
            .clickable {
                if (favoriteGenres?.any { it.id == genre.id } == true) {
                    vm.deleteFromFavoriteGenres(genre)
                } else {
                    vm.addToFavoriteGenres(genre)
                }
            },
        fontFamily = FontFamily(Font(R.font.manrope)),
        fontSize = 16.sp,
    )
}