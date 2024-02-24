package com.projects.movieflix.domain.usecase

import com.projects.movieflix.domain.repository.MovieRepository
import javax.inject.Inject

class GetAllMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int, fetchFromRemote: Boolean) =
        repository.getMovies(page, fetchFromRemote)
}