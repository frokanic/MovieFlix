package com.projects.movieflix.domain.model

import android.graphics.Bitmap

data class Movie(
    val id: Int,
    val title: String,
    val rating: Double,
    val imageUrl: String,
    val releaseDate: String,
    val page: Int,
    val imageFromLocal: Bitmap? = null,
    val isFavorite: Boolean = false
)
