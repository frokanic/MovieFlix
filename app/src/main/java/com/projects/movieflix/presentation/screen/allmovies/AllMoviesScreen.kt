package com.projects.movieflix.presentation.screen.allmovies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.projects.movieflix.domain.model.Movie
import com.projects.movieflix.presentation.screen.allmovies.components.MovieCard
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.movieflix.presentation.navigation.Screen
import com.projects.movieflix.presentation.screen.allmovies.components.ErrorView
import com.projects.movieflix.presentation.screen.allmovies.components.MovieList
import com.projects.movieflix.presentation.sharedcomponents.LoadingView

@Composable
fun AllMoviesScreen(
    navController: NavController,
    viewModel: AllMoviesViewModel = hiltViewModel()
) {
    val screenState = viewModel.screenState.collectAsState().value


    Box(modifier = Modifier.fillMaxSize()) {
        when {
            screenState.isLoading && !screenState.isLoadingMore -> LoadingView()
            screenState.error != null -> ErrorView(errorResId = screenState.error)
            else -> MovieList(navController, movies = screenState.movies, viewModel = viewModel)
        }
    }
}