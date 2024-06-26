package com.projects.movieflix.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.projects.movieflix.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movies")
    suspend fun clearAll()

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun removeMovieById(movieId: String)

    @Query("DELETE FROM movies WHERE id NOT IN (:movieIds)")
    suspend fun removeMoviesNotInList(movieIds: List<Int>)

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT id FROM movies WHERE isFavorite = 1")
    suspend fun getFavoriteMovieIds(): List<Int>

    @Query("SELECT * FROM movies WHERE page = :page")
    fun getMoviesByPage(page: Int): Flow<List<MovieEntity>>

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getNumberOfEntities(): Int

    @Query("SELECT isFavorite FROM movies WHERE id = :movieId")
    suspend fun isFavorite(movieId: Int): Boolean

    @Query("SELECT id FROM movies WHERE isFavorite = 1")
    suspend fun getFavorites(): List<Int>

    @Query("SELECT COUNT(id) FROM movies WHERE id = :movieId")
    suspend fun getCountById(movieId: Int): Int

    @Query("UPDATE movies SET isFavorite = NOT isFavorite WHERE id = :movieId")
    suspend fun addRemoveFavorites(movieId: Int)
}