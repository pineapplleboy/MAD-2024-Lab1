package com.example.moviecatalog.app.presentation.ui.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.moviecatalog.R

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