package com.example.moviecatalog.app.presentation.ui.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R

@Composable
fun MovieRating(
    modifier: Modifier = Modifier
) {
    MoviePanel(
        iconID = R.drawable.star,
        textID = R.string.rating,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            RatingScore(
                value = 9.9f,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
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
            ) {
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
            ) {
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