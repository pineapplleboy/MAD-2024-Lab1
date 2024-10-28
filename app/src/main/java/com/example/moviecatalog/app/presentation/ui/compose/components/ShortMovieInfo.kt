package com.example.moviecatalog.app.presentation.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.R

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