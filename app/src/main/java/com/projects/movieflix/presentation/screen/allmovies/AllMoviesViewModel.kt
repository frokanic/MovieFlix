package com.projects.movieflix.presentation.screen.allmovies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.movieflix.domain.usecase.GetAllMoviesUseCase
import com.projects.movieflix.domain.usecase.UpdateFavoriteStatusUseCase
import com.projects.movieflix.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllMoviesViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase
) : ViewModel() {

    var curPage = 1

    private val _screenState = MutableStateFlow(AllMoviesState())
    val screenState: StateFlow<AllMoviesState> = _screenState.asStateFlow()

    init {
        fetchMovies(1, true)
    }

    fun fetchMovies(page: Int, fetchFromRemote: Boolean) {
        viewModelScope.launch {
            if (page > 1) {
                _screenState.value = _screenState.value.copy(isLoadingMore = true)
            } else if (page == 1) {
                _screenState.value = _screenState.value.copy(isLoading = true)
            }

            getAllMoviesUseCase(page, fetchFromRemote).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val currentMovieIds = _screenState.value.movies.map { it.id }.toSet()
                        val newMovieIds = result.data?.map { it.id }?.toSet()
                        result.data?.toString()?.let { Log.d("AllMoviesViewModel", it) }
                        if (currentMovieIds != newMovieIds) {
                            val currentMovies =
                                if (page == 1) emptyList() else _screenState.value.movies
                            val newMovies = result.data ?: emptyList()
                            _screenState.value = _screenState.value.copy(
                                movies = currentMovies + newMovies,
                                isLoading = false,
                                isLoadingMore = false,
                                fallbackCode = result.fallbackCode
                            )
                        }
                    }
                    is Resource.Error -> {
                        _screenState.value = _screenState.value.copy(
                            error = result.errorResId,
                            isLoading = false,
                            isLoadingMore = false
                        )
                    }
                    is Resource.Loading -> {
                        _screenState.value = _screenState.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    fun loadNextPage() {
        curPage++
        fetchMovies(curPage, true)
    }

    fun updateFavorite(movieId: Int) {
        viewModelScope.launch {
            // Optimistic UI update
            val updatedMovies = _screenState.value.movies.map { movie ->
                if (movie.id == movieId) movie.copy(isFavorite = !movie.isFavorite) else movie
            }
            _screenState.value = _screenState.value.copy(movies = updatedMovies)

            updateFavoriteStatusUseCase(movieId)
        }
    }
}