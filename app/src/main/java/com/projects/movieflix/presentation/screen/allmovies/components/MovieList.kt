package com.projects.movieflix.presentation.screen.allmovies.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.projects.movieflix.domain.model.Movie
import com.projects.movieflix.presentation.navigation.Screen
import com.projects.movieflix.presentation.screen.allmovies.AllMoviesViewModel

@Composable
fun MovieList(
    navController: NavController,
    movies: List<Movie>,
    viewModel: AllMoviesViewModel
) {
    val state = viewModel.screenState.collectAsState().value
    val isLoadingMore = state.isLoadingMore

    LazyColumn {
        itemsIndexed(movies, key = { index, movie -> movie.id }) { index, movie ->
            MovieCard(
                movieImagePainter = rememberAsyncImagePainter(model = movie.imageUrl),
                movieTitle = movie.title,
                movieReleaseDate = movie.releaseDate,
                rating = movie.rating * 0.5,
                isFavorite = movie.isFavorite,
                onFavoriteChange = {
                    viewModel.updateFavorite(movie.id)
                },
                onItemClick = {
                    navController.navigate(Screen.MovieDetailsScreen.createRoute(movie.id))
                }
            )
            if (index == movies.lastIndex && !isLoadingMore) {
                viewModel.loadNextPage()
            }
        }
        if (isLoadingMore) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Loading...", fontSize = 12.sp)
                }
            }
        }
    }
}