package com.example.moviecatalog.app.presentation.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.R

@Composable
fun InformationPanelElement(
    modifier: Modifier = Modifier,
    name: String,
    content: String
) {
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
    ) {
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