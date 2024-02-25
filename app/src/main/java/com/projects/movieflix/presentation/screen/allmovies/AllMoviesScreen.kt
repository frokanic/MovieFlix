package com.projects.movieflix.presentation.screen.allmovies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import com.projects.movieflix.presentation.screen.allmovies.components.ErrorView
import com.projects.movieflix.presentation.screen.allmovies.components.MovieList
import com.projects.movieflix.presentation.sharedcomponents.LoadingView

@Composable
fun AllMoviesScreen(
    navController: NavController,
    viewModel: AllMoviesViewModel = hiltViewModel()
) {
    val screenState = viewModel.screenState.collectAsState().value

    val comingFromDetails = navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>("comingFromDetails")?.observeAsState()

    LaunchedEffect(comingFromDetails?.value) {
        if (comingFromDetails?.value == true) {
            viewModel.refreshFavoriteStatuses()
            navController.currentBackStackEntry?.savedStateHandle?.remove<Boolean>("comingFromDetails")
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        when {
            screenState.isLoading && !screenState.isLoadingMore -> LoadingView()
            screenState.error != null -> ErrorView(errorResId = screenState.error)
            else -> MovieList(navController, movies = screenState.movies, viewModel = viewModel)
        }
    }
}