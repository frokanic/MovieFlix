package com.projects.movieflix.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.projects.movieflix.data.local.entity.MovieEntity

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
    suspend fun getMoviesByPage(page: Int): List<MovieEntity>

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getNumberOfEntities(): Int

    @Query("UPDATE movies SET isFavorite = NOT isFavorite WHERE id = :movieId")
    suspend fun addRemoveFavorites(movieId: Int)

    @Query("SELECT isFavorite FROM movies WHERE id = :movieId")
    suspend fun isFavorite(movieId: Int): Boolean
}