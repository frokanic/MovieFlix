package com.projects.movieflix.presentation.screen.moviedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.projects.cinesphere.presentation.screen.moviedetails.components.MovieHeader
import com.projects.movieflix.presentation.extension.toHourMinuteFormat
import com.projects.movieflix.presentation.navigation.Screen
import com.projects.movieflix.presentation.screen.moviedetails.components.DetailsTopBar
import com.projects.movieflix.presentation.screen.moviedetails.components.ErrorInfo
import com.projects.movieflix.presentation.screen.moviedetails.components.MovieInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    navController: NavController,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.movieDetailsState.collectAsState().value

    Column(modifier = Modifier.fillMaxSize()) {
        DetailsTopBar(
            onBackPressed = {
                navController.popBackStack(Screen.MovieDetailsScreen.route, true)
            }
        )

        when {
            state.isLoading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            state.error != null -> {
                val refreshState = rememberPullToRefreshState()

                ErrorInfo(state.error)

                PullToRefreshContainer(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    state = refreshState
                )
            }
            state.movieDetails != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item {
                            Image(
                                painter = rememberAsyncImagePainter(model = state.movieDetails.backdropPath),
                                contentDescription = "Movie Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .height(250.dp)
                            )

                            MovieHeader(
                                movieTitle = state.movieDetails.title,
                                isFavorite = state.movieDetails.isFavorite,
                                genres = state.movieDetails.genres.joinToString(", "),
                                releaseDate = state.movieDetails.releaseDate,
                                rating = state.movieDetails.voteAverage * 0.5,
                                onFavoriteChange = { viewModel.updateFavorite(state.movieDetails.id) }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            MovieInfo(
                                runtime = state.movieDetails.runtime!!.toHourMinuteFormat(),
                                description = state.movieDetails.overview,
                                cast = state.movieDetails.cast,
                                reviews = state.movieDetails.reviews,
                                similarMovies = state.movieDetails.similarMovies,
                                onSimilarMoviePressed = {}
                            )
                        }
                    }
                }
            }
        }
    }
}

