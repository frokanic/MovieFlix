package com.projects.cinesphere.presentation.screen.moviedetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projects.movieflix.presentation.sharedcomponents.FavoriteHeartIcon
import com.projects.movieflix.presentation.sharedcomponents.RatingBar

@Composable
fun MovieHeader(
    movieTitle: String,
    isFavorite: Boolean,
    genres: String,
    releaseDate: String,
    rating: Double,
    modifier: Modifier = Modifier,
    onFavoriteChange: (Boolean) -> Unit
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = movieTitle,
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = genres,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            FavoriteHeartIcon(
                isFavorite = isFavorite,
                onFavoriteChange = onFavoriteChange
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = releaseDate,
            color = Color(0xFFD2B48C),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        RatingBar(rating = rating)
    }
}

@Preview(showBackground = true)
@Composable
fun MovieHeaderPreview() {
    MovieHeader(
        movieTitle = "Interstellar",
        isFavorite = false,
        genres = "Adventure, Drama, Sci-Fi",
        releaseDate = "15 December 2014",
        rating = 3.8,
        onFavoriteChange = {}
    )
}