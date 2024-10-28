package com.example.moviecatalog.app.presentation.ui.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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