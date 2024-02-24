package com.projects.movieflix.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "movies"
)
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String?,
    val imageUrl: String?,
    val releaseDate: String?,
    val rating: Double?,
    val page: Int,
    val isFavorite: Boolean = false
)