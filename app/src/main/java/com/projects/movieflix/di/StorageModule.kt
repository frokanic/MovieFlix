package com.projects.movieflix.di

import android.content.Context
import androidx.room.Room
import com.projects.movieflix.data.local.MovieDao
import com.projects.movieflix.data.local.MovieDatabase
import com.projects.movieflix.data.local.PhotoStorageManagerImpl
import com.projects.movieflix.domain.local.PhotoStorageManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "Movies.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePhotoStorageManager(@ApplicationContext appContext: Context): PhotoStorageManager {
        return PhotoStorageManagerImpl(appContext)
    }

    @Provides
    @Singleton
    fun provideEventDao(database: MovieDatabase): MovieDao {
        return database.dao
    }

}