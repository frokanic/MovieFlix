package com.projects.movieflix.presentation.screen.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.movieflix.domain.usecase.GetMovieDetailsUseCase
import com.projects.movieflix.domain.usecase.FavoriteStatusUseCase
import com.projects.movieflix.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val favoriteStatusUseCase: FavoriteStatusUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _movieDetailsState = MutableStateFlow(MovieDetailsState())
    val movieDetailsState: StateFlow<MovieDetailsState> = _movieDetailsState.asStateFlow()

    var movieId: Int? = null

    init {
        movieId = savedStateHandle.get<Int>("movieId") ?: throw IllegalArgumentException("MovieId not found")
        fetchMovieDetails(movieId!!)
    }

    fun fetchMovieDetails(id: Int) {
        viewModelScope.launch {
            getMovieDetailsUseCase(id).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _movieDetailsState.value = MovieDetailsState(isLoading = true, error= null)
                    }
                    is Resource.Success -> {
                        _movieDetailsState.value = MovieDetailsState(
                            isLoading = false,
                            movieDetails = resource.data,
                            error= null
                        )
                    }
                    is Resource.Error -> {
                        _movieDetailsState.value = MovieDetailsState(
                            isLoading = false,
                            error = resource.errorResId
                        )
                    }
                }
            }
        }
    }

    fun updateFavorite(movieId: Int) {
        val currentDetails = _movieDetailsState.value.movieDetails ?: return

        _movieDetailsState.value = _movieDetailsState.value.copy(
            movieDetails = currentDetails.copy(isFavorite = !currentDetails.isFavorite)
        )

        viewModelScope.launch {
            favoriteStatusUseCase.addRemoveFavorites(movieId)
        }
    }
}