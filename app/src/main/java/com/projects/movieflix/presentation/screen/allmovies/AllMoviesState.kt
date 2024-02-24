package com.projects.movieflix.presentation.screen.allmovies

import com.projects.movieflix.domain.model.Movie

data class AllMoviesState(
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: Int? = null,
    val fallbackCode: Int? = null
)