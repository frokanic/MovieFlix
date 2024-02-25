package com.projects.movieflix.presentation.screen.moviedetails

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.projects.cinesphere.presentation.screen.moviedetails.components.MovieHeader
import com.projects.movieflix.presentation.extension.toHourMinuteFormat
import com.projects.movieflix.presentation.navigation.Screen
import com.projects.movieflix.presentation.screen.moviedetails.components.DetailsTopBar
import com.projects.movieflix.presentation.screen.moviedetails.components.ErrorInfo
import com.projects.movieflix.presentation.screen.moviedetails.components.MovieInfo
import com.projects.movieflix.utils.extensionfunctions.toJson
import dev.materii.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import dev.materii.pullrefresh.DragRefreshIndicator
import dev.materii.pullrefresh.DragRefreshLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    navController: NavController,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.movieDetailsState.collectAsState().value

    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            scope.launch {
                isRefreshing = true
                viewModel.movieId?.let {
                    viewModel.fetchMovieDetails(it)
                }
                delay(3000)
                isRefreshing = false
            }

        }
    )

    Column(modifier = Modifier.fillMaxSize()) {
        DetailsTopBar(
            onBackPressed = {
//                navController.popBackStack(Screen.MovieDetailsScreen.route, true)
                navController.previousBackStackEntry?.savedStateHandle?.apply {
                    set("comingFromDetails", true)
                }
                navController.popBackStack()
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
                DragRefreshLayout(
                    modifier = Modifier.fillMaxSize(),
                    state = pullRefreshState,
                    flipped = false,
                    indicator = {
                        DragRefreshIndicator(
                            state = pullRefreshState,
                            flipped = false,
                            color = Color.White
                        )
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        contentAlignment = Alignment.Center
                    ) {
                        ErrorInfo(state.error)
                    }
                }
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
                            Box(
                                contentAlignment = Alignment.BottomEnd,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = state.movieDetails.backdropPath),
                                    contentDescription = "Movie Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.matchParentSize()
                                )
                                IconButton(
                                    onClick = {
                                        val sendIntent: Intent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(Intent.EXTRA_TEXT, state.movieDetails.toJson())
                                            type = "text/plain"
                                        }
                                        val shareIntent = Intent.createChooser(sendIntent, null)
                                        context.startActivity(shareIntent)
                                    },
                                    modifier = Modifier
                                        .size(60.dp)
                                        .padding(12.dp)
                                        .background(Color.White, shape = CircleShape)
                                        .padding(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Share,
                                        contentDescription = "Share",
                                        tint = Color.Black,
                                        modifier = Modifier.size(42.dp)
                                    )
                                }
                            }

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
                                onSimilarMoviePressed = {
                                    navController.navigate(Screen.MovieDetailsScreen.createRoute(it))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}