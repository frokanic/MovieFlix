package com.projects.movieflix.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val genres: List<String>,
    val posterPath: String,
    val backdropPath: String,
    val voteAverage: Double,
    val releaseDate: String,
    val homepage: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val runtime: Int?,
    val spokenLanguages: List<String>,
    val status: String,
    val video: Boolean,
    val voteCount: Int,
    val cast: List<String>,
    val reviews: List<Review>,
    val similarMovies: List<Movie>,
    val isFavorite: Boolean = false
)

data class Review(
    val author: String,
    val content: String
)