package com.projects.movieflix.domain.usecase

import com.projects.movieflix.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(id: Int) = repository.getMovieDetails(id)
}
