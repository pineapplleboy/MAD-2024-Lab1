package com.example.moviecatalog.app.presentation.ui.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.moviecatalog.R
import com.example.moviecatalog.domain.model.Review
import com.example.moviecatalog.domain.model.ReviewShort
import com.example.moviecatalog.domain.model.UserShort

@Composable
fun ReviewPanel(
    reviews: List<Review>,
    modifier: Modifier = Modifier,
    addFriend: (UserShort) -> Unit
) {

    var currentReview by remember {
        mutableIntStateOf(0)
    }

    MoviePanel(
        iconID = R.drawable.reviews,
        textID = R.string.reviews,
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if(reviews.isNotEmpty()){
                ReviewElement(review = reviews[currentReview]){
                    addFriend(it)
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .height(40.dp)
            ){
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    colorResource(id = R.color.orange_dark),
                                    colorResource(id = R.color.orange_bright)
                                )
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                ){
                    Text(
                        text = stringResource(R.string.add_review),
                        fontFamily = FontFamily(Font(R.font.manrope_bold)),
                        color = colorResource(id = R.color.white),
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    IconButton(
                        onClick = {
                            currentReview = if(currentReview > 0) (currentReview - 1) else 0
                        },
                        modifier = Modifier
                            .background(
                                color = colorResource(id = if(currentReview > 0) R.color.dark else R.color.dark_faded),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.chevron_left),
                            contentDescription = stringResource(R.string.left_review),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            currentReview = if(currentReview < reviews.size - 1) (currentReview + 1) else (reviews.size - 1)
                        },
                        modifier = Modifier
                            .background(
                                color = colorResource(id = if(currentReview < reviews.size - 1) R.color.dark else R.color.dark_faded),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.chevron_right),
                            contentDescription = stringResource(R.string.right_review),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReviewElement(
    review: Review,
    addFriend: (UserShort) -> Unit
){
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.dark),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = if(review.author.avatar != null)
                    rememberAsyncImagePainter(review.author.avatar)
                else
                    painterResource(
                        id = R.drawable.default_profile_icon
                    ),
                contentDescription = stringResource(R.string.user_s_avatar),
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickable {
                        addFriend(review.author)
                    },
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = review.author.nickName ?: "",
                    fontFamily = FontFamily(Font(R.font.manrope)),
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.white),
                    modifier = Modifier.clickable {
                        addFriend(review.author)
                    }
                )
                Text(
                    text = review.createDateTime,
                    fontFamily = FontFamily(Font(R.font.manrope)),
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.gray_faded)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        color = colorResource(id = R.color.orange_bright),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = review.rating.toString(),
                    fontFamily = FontFamily(Font(R.font.manrope)),
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.white)
                )
            }
        }

        Text(
            text = review.reviewText ?: "",
            fontFamily = FontFamily(Font(R.font.manrope)),
            fontSize = 14.sp,
            color = colorResource(id = R.color.white)
        )
    }
}