package com.projects.movieflix.domain.local

import com.projects.movieflix.domain.model.InternalStoragePhoto

interface PhotoStorageManager {
    suspend fun savePhoto(filename: String, imageUrl: String): Boolean
    suspend fun loadPhotos(): List<InternalStoragePhoto>
    suspend fun deletePhoto(filename: String): Boolean
    suspend fun clearAllPhotos(): Boolean
}
