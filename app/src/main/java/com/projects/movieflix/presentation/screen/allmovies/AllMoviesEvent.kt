package com.projects.movieflix.presentation.screen.allmovies

sealed class AllMoviesEvent {
    data object Refresh : AllMoviesEvent()
}