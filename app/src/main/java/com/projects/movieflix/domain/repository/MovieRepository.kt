package com.projects.movieflix.domain.repository

import com.projects.movieflix.domain.model.Movie
import com.projects.movieflix.domain.model.MovieDetails
import com.projects.movieflix.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovies(page: Int, fetchFromRemote: Boolean): Flow<Resource<List<Movie>>>

    suspend fun addRemoveFavorites(id: Int)

    suspend fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>>

    suspend fun getFavorites(): List<Int>
}