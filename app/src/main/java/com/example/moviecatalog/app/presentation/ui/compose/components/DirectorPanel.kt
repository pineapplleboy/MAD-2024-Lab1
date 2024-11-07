package com.example.moviecatalog.app.presentation.ui.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.R

@Composable
fun DirectorPanel(
    name: String,
    image: String?,
    modifier: Modifier = Modifier
) {
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
        ) {
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