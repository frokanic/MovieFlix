package com.projects.movieflix.presentation.screen.moviedetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.projects.movieflix.domain.model.Movie
import com.projects.movieflix.domain.model.Review

@Composable
fun MovieInfo(
    runtime: String,
    description: String,
    cast: List<String>,
    reviews: List<Review>,
    similarMovies: List<Movie>,
    modifier: Modifier = Modifier,
    onSimilarMoviePressed: (Int) -> Unit
) {
    Column(modifier = modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth()
    ) {
        Text(
            text = "Runtime",
            color = Color(0xFF5E35B1),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = runtime,
            color = Color(0xFFD2B48C),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Description",
            color = Color(0xFF5E35B1),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Cast",
            color = Color(0xFF5E35B1),
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row {
            var castMembers = cast.joinToString(separator = ", ")

            Text(
                text = castMembers,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(end = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Reviews",
            color = Color(0xFF5E35B1),
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(6.dp))

        reviews.take(3).forEach { review ->
            Text(
                text = review.author,
                color = Color(0xFFFFA500),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "\"${review.content}\"", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Similar Movies", style = MaterialTheme.typography.headlineSmall)

        LazyRow {
            items(similarMovies.size) { index ->
                // Get the movie from the list using the index
                val movie = similarMovies[index]

                Image(
                    painter = rememberAsyncImagePainter(model = movie.imageUrl),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .size(120.dp, 180.dp)
                        .padding(end = 8.dp)
                        .clickable {
                            onSimilarMoviePressed(movie.id)
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun MovieInfoPreview(

){
    MovieInfo(
        runtime = "2h 28min",
        description = "Peter Parker is unmasked and no longer able to separate his normal life from the high stakes of being a superhero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
        cast = listOf("Tom Holland", "Zendaya", "Benedict Cumberbatch"),
        reviews = listOf(
            Review(
                author = "Chris Stuckmann",
                content = "Spider-Man: No Way Home isn't without its flaws, but it is definitely worth the adventure to the theatre."
            ),
            Review(
                author = "John Campea",
                content = "This is another Spider-Man for kids which for me is a perfect Saturday. Would very much like a real web slinger instead of this kid."
            ),
            Review(
                author = "Jane Doe",
                content = "An impressive outing for Spider-Man, balancing fan service with solid storytelling."
            )
        ),
        similarMovies = listOf(
            Movie(id = 1, title = "Similar Movie 1", rating = 4.5, imageUrl = "https://via.placeholder.com/120x180", releaseDate = "2022", page = 1, isFavorite = false),
            Movie(id = 2, title = "Similar Movie 2", rating = 4.8, imageUrl = "https://via.placeholder.com/120x180", releaseDate = "2023", page = 2, isFavorite = false)
        ),
        onSimilarMoviePressed = {}
    )
}