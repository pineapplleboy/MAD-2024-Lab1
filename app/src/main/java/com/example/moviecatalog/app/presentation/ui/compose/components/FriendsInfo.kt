package com.example.moviecatalog.app.presentation.ui.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.moviecatalog.R
import com.example.moviecatalog.domain.model.UserShort

@Composable
fun FriendsInfo(
    friends: List<UserShort>,
    modifier: Modifier = Modifier
){
    if(friends.isNotEmpty()){
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
                painter = if(friends[0].avatar != null)
                    rememberAsyncImagePainter(friends[0].avatar)
                else
                    painterResource(
                        id = R.drawable.default_profile_icon
                    ),
                contentDescription = stringResource(R.string.friend_avatar),
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            if(friends.size > 1){
                Image(
                    painter = if(friends[1].avatar != null)
                        rememberAsyncImagePainter(friends[1].avatar)
                    else
                        painterResource(
                            id = R.drawable.default_profile_icon
                        ),
                    contentDescription = stringResource(R.string.friend_avatar),
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            if(friends.size > 2){
                Image(
                    painter = if(friends[2].avatar != null)
                        rememberAsyncImagePainter(friends[2].avatar)
                    else
                        painterResource(
                            id = R.drawable.default_profile_icon
                        ),
                    contentDescription = stringResource(R.string.friend_avatar),
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

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
}