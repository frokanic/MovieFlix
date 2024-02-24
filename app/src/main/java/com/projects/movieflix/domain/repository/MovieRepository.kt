package com.projects.movieflix.domain.repository

import androidx.paging.PagingData
import com.projects.movieflix.domain.model.InternalStoragePhoto
import com.projects.movieflix.domain.model.Movie
import com.projects.movieflix.domain.model.MovieDetails
import com.projects.movieflix.domain.model.MovieType
import com.projects.movieflix.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovies(page: Int, fetchFromRemote: Boolean): Flow<Resource<List<Movie>>>

    suspend fun addRemoveFavorites(id: Int)

    suspend fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>>

}