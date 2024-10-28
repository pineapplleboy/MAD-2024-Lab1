package com.example.moviecatalog.app.presentation.ui.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R
import com.example.moviecatalog.app.presentation.viewmodel.MovieDetailsViewModel
import com.example.moviecatalog.domain.model.Genre

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenresPanel(
    genres: List<Genre>,
    vm: MovieDetailsViewModel,
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
                    genre = genre,
                    vm = vm
                )
            }
        }
    }
}