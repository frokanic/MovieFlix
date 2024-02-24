package com.projects.movieflix.presentation.screen.moviedetails

import com.projects.movieflix.domain.model.MovieDetails

data class MovieDetailsState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetails? = null,
    val error: Int? = null
)