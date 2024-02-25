package com.projects.movieflix.domain.usecase

import com.projects.movieflix.domain.repository.MovieRepository
import javax.inject.Inject

class FavoriteStatusUseCase@Inject constructor(
    private val repository: MovieRepository
) {
    suspend fun addRemoveFavorites(id: Int) = repository.addRemoveFavorites(id)

    suspend fun getFavorites() = repository.getFavorites()
}