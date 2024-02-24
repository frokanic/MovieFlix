package com.projects.movieflix.data.mapper

import android.graphics.Bitmap
import com.projects.movieflix.data.local.entity.MovieEntity
import com.projects.movieflix.data.remote.response.MovieDetailsResponse
import com.projects.movieflix.data.remote.response.MoviesResponse
import com.projects.movieflix.data.remote.response.Result
import com.projects.movieflix.data.remote.response.ReviewsResponse
import com.projects.movieflix.domain.model.Movie
import com.projects.movieflix.domain.model.MovieDetails
import com.projects.movieflix.domain.model.Review
import com.projects.movieflix.utils.extensionfunctions.reformatDate

fun Result.toMovieEntity(page: Int, isFavorite: Boolean = false): MovieEntity = MovieEntity(
    id = this.id,
    title = this.title,
    rating = this.vote_average,
    imageUrl = "https://image.tmdb.org/t/p/w500${this.backdrop_path}",
    releaseDate = this.release_date.reformatDate("yyyy-MM-dd", "dd MMMM yyyy"),
    page = page,
    isFavorite = isFavorite
)

fun MovieEntity.toDomainModel(imageFromLocal: Bitmap? = null): Movie {
    return Movie(
        id = this.id,
        title = this.title ?: "Unknown",
        rating = this.rating ?: 0.0,
        imageUrl = this.imageUrl ?: "",
        releaseDate = this.releaseDate ?: "Unknown Date",
        page = this.page,
        imageFromLocal = imageFromLocal,
        isFavorite = this.isFavorite
    )
}

fun detailsToDomainModel(
    details: MovieDetailsResponse,
    reviews: ReviewsResponse,
    similarMovies: MoviesResponse,
    isFavorite: Boolean
): MovieDetails = MovieDetails(
    id = details.id,
    title = details.title,
    overview = details.overview,
    genres = details.genres.map { it.name },
    posterPath = "https://image.tmdb.org/t/p/w500${details.poster_path}",
    backdropPath = "https://image.tmdb.org/t/p/w500${details.backdrop_path}",
    voteAverage = details.vote_average,
    releaseDate = details.release_date.reformatDate("yyyy-MM-dd", "dd MMMM yyyy"),
    homepage = details.homepage,
    originalLanguage = details.original_language,
    originalTitle = details.original_title,
    runtime = details.runtime,
    spokenLanguages = details.spoken_languages.map { it.english_name },
    status = details.status,
    video = details.video,
    voteCount = details.vote_count,
    cast = details.credits.cast.map {cast ->
                                    cast.name
    },
    reviews = reviews.results.take(3).map { review ->
        Review(
            author = review.author,
            content = review.content
        )
    },
    similarMovies = similarMovies.results.map { result ->
        Movie(
            id = result.id,
            title = result.title,
            rating = result.vote_average,
            imageUrl = "https://image.tmdb.org/t/p/w500${result.poster_path}",
            releaseDate = result.release_date,
            page = similarMovies.page
        )
    },
    isFavorite = isFavorite
)