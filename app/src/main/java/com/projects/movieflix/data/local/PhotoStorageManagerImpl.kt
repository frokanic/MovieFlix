package com.projects.movieflix.data.local

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.projects.movieflix.domain.local.PhotoStorageManager
import com.projects.movieflix.domain.model.InternalStoragePhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class PhotoStorageManagerImpl(private val context: Context) : PhotoStorageManager {

    override suspend fun savePhoto(filename: String, imageUrl: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val inputStream = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            connection.disconnect()

            context.openFileOutput(filename, Context.MODE_PRIVATE).use { stream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun loadPhotos(): List<InternalStoragePhoto> = withContext(Dispatchers.IO) {
        val files = context.filesDir.listFiles()
        files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") }?.mapNotNull { file ->
            try {
                if (!file.exists()) {
                    Log.e("PhotoStorageManagerImpl", "File does not exist: ${file.absolutePath}")
                    return@mapNotNull null
                }
                val bytes = file.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(file.name, bmp)
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        } ?: listOf()
    }

    override suspend fun deletePhoto(filename: String): Boolean = withContext(Dispatchers.IO) {
        try {
            context.deleteFile(filename)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun clearAllPhotos(): Boolean = withContext(Dispatchers.IO) {
        try {
            val files = context.filesDir.listFiles()
            val imageFiles = files?.filter { it.isFile && it.name.endsWith(".jpg") } ?: emptyList()

            for (file in imageFiles) {
                if (!file.delete()) {
                    throw IOException("Failed to delete ${file.name}")
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}