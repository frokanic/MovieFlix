package com.projects.movieflix.data.repository

import androidx.room.withTransaction
import com.projects.movieflix.data.local.MovieDatabase
import com.projects.movieflix.data.mapper.detailsToDomainModel
import com.projects.movieflix.data.mapper.toDomainModel
import com.projects.movieflix.data.mapper.toMovieEntity
import com.projects.movieflix.data.remote.MovieApi
import com.projects.movieflix.domain.local.PhotoStorageManager
import com.projects.movieflix.data.mapper.error.ErrorMapper
import com.projects.movieflix.domain.model.Movie
import com.projects.movieflix.domain.model.MovieDetails
import com.projects.movieflix.domain.repository.MovieRepository
import com.projects.movieflix.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val database: MovieDatabase,
    private val photoStorage: PhotoStorageManager,
    private val errorMapper: ErrorMapper
) : MovieRepository {

     override suspend fun getMovies(
        page: Int,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())

         if (fetchFromRemote) {
             try {
                 val response = api.getPopularMovies(page)

                 database.withTransaction {
                     val favoriteIds = database.dao.getFavoriteMovieIds()

                     if (page == 1) {
                         database.dao.clearAll()
                         photoStorage.clearAllPhotos()
                     }

                     val movieEntities = response.results.map {
                         it.toMovieEntity(page, favoriteIds.contains(it.id))
                     }

                     database.dao.insertMovies(movieEntities)

                     movieEntities.forEach { movieEntity ->
                         if (movieEntity.imageUrl != null && movieEntity.page == 1) {
                             photoStorage.savePhoto(
                                 "${movieEntity.id}.jpg",
                                 movieEntity.imageUrl
                             )
                         }
                     }
                 }

                 emitAll(database.dao.getMoviesByPage(page).map { entities ->
                     Resource.Success(entities.map { it.toDomainModel() })
                 })
             } catch (e: Exception) {
                 val fallbackResult = database.dao.getMoviesByPage(1)
                 val cachedPhotos = photoStorage.loadPhotos()

                 emitAll(fallbackResult
                     .map { entities ->
                         val moviesWithPhotos = entities.mapNotNull { movieEntity ->
                             val photo = cachedPhotos.find { it.filename.startsWith("${movieEntity.id}.jpg") }
                             photo?.let {
                                 Movie(
                                     id = movieEntity.id,
                                     title = movieEntity.title ?: "Unknown",
                                     rating = movieEntity.rating ?: 0.0,
                                     imageUrl = movieEntity.imageUrl ?: "",
                                     releaseDate = movieEntity.releaseDate ?: "Unknown",
                                     page = movieEntity.page,
                                     imageFromLocal = photo.bitmap,
                                     isFavorite = movieEntity.isFavorite
                                 )
                             }
                         }
                         if (moviesWithPhotos.isNotEmpty()) {
                             Resource.Success(moviesWithPhotos, errorMapper.mapError(e))
                         } else {
                             Resource.Error(errorMapper.mapError(e))
                         }
                     }
                 )
             }
         } else {
             try {
                 emitAll(database.dao.getMoviesByPage(page).map { entities ->
                     Resource.Success(entities.map { it.toDomainModel() })
                 })
             } catch(e: Exception) {
                 getMovies(page, true)
             }
         }
    }

    override suspend fun addRemoveFavorites(id: Int) =
        withContext(Dispatchers.IO) {
            database.dao.addRemoveFavorites(id)
        }

    override suspend fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>> = flow {
        emit(Resource.Loading())
        try {
            coroutineScope {
                val detailsDeferred = async { api.getMovieDetails(id) }
                val reviewsDeferred = async { api.getMovieReviews(id, 1) }
                val similarMoviesDeferred = async { api.getSimilarMovies(id, 1) }
                val isFavoriteDeferred = async { database.dao.isFavorite(id) }

                val details = detailsDeferred.await()
                val reviews = reviewsDeferred.await()
                val similarMovies = similarMoviesDeferred.await()
                val isFavorite = isFavoriteDeferred.await()

                val movieDetails = detailsToDomainModel(details, reviews, similarMovies, isFavorite)
                emit(Resource.Success(movieDetails))
            }
        } catch (e: Exception) {
            emit(Resource.Error(errorMapper.mapError(e)))
        }
    }

    override suspend fun getFavorites(): List<Int> = database.dao.getFavorites()
}