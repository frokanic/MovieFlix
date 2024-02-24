package com.projects.movieflix.presentation.screen.allmovies.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.movieflix.R
import com.projects.movieflix.presentation.sharedcomponents.FavoriteHeartIcon
import com.projects.movieflix.presentation.sharedcomponents.RatingBar

@Composable
fun MovieCard(
    movieImagePainter: Painter,
    movieTitle: String,
    movieReleaseDate: String,
    rating: Double,
    isFavorite: Boolean,
    onFavoriteChange: (Boolean) -> Unit,
    onItemClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = Modifier.padding(16.dp).clickable(onClick = onItemClick),
        border = BorderStroke(6.dp, Color(0xFFF6B942))
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                painter = movieImagePainter,
                contentDescription = "Movie Image",
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .verticalGradientScrim(color = Color.Black) // Gradient scrim modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(start = 26.dp, bottom = 20.dp, end = 16.dp) // Adjusted padding
            ) {
                Text(
                    text = movieTitle,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(
                            top = 24.dp, bottom = 8.dp, end = 8.dp) // Adjusted padding
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RatingBar(
                        rating = rating,
                        starsColor = Color.Yellow,
                        modifier = Modifier.size(18.dp) // Adjusted size of the stars
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Space between stars and date
                    Text(
                        text = movieReleaseDate,
                        color = Color.White,
                        fontSize = 12.sp, // Adjust if needed to match your design
                        fontWeight = FontWeight.Normal, // Use FontWeight.Light or FontWeight.Thin if it matches your design better
                        letterSpacing = 0.5.sp, // Adjust the letter spacing if needed
                        modifier = Modifier.padding(start = 6.dp) // Add some start padding if needed
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp), // Padding from the edge of the card
                contentAlignment = Alignment.BottomEnd // Align the icon to the bottom end
            ) {
                FavoriteHeartIcon(
                    isFavorite = isFavorite,
                    onFavoriteChange = onFavoriteChange
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieCardPreview() {

    MovieCard(
        movieImagePainter = painterResource(id = R.drawable.dummy_image),
        movieTitle = "Avatar: The Way of Water",
        movieReleaseDate = "5 May 2022",
        rating = 4.0,
        isFavorite = true,
        onFavoriteChange = {},
        onItemClick = {}
    )
}

fun Modifier.verticalGradientScrim(
    color: Color,
    startYPercentage: Float = 0.35f,
    endYPercentage: Float = 0f
) = drawBehind {
    val startY = size.height * startYPercentage
    val endY = size.height * endYPercentage
    drawRect(
        Brush.verticalGradient(
            colors = listOf(color.copy(alpha = 0.8f), color.copy(alpha = 0f)),
            startY = startY,
            endY = endY
        )
    )
}