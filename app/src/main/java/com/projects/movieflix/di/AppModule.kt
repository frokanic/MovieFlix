package com.projects.movieflix.di

import com.projects.movieflix.data.local.MovieDatabase
import com.projects.movieflix.data.mapper.error.ErrorMapper
import com.projects.movieflix.data.mapper.error.ErrorMapperImpl
//import com.projects.movieflix.data.mapper.error.ErrorMapperImpl
import com.projects.movieflix.data.remote.MovieApi
import com.projects.movieflix.data.repository.MovieRepositoryImpl
import com.projects.movieflix.domain.local.PhotoStorageManager
//import com.projects.movieflix.data.mapper.error.ErrorMapper
import com.projects.movieflix.domain.repository.MovieRepository
import com.projects.movieflix.domain.usecase.GetAllMoviesUseCase
import com.projects.movieflix.domain.usecase.GetMovieDetailsUseCase
import com.projects.movieflix.domain.usecase.UpdateFavoriteStatusUseCase
//import com.projects.movieflix.domain.usecase.GetAllMoviesUseCase
//import com.projects.movieflix.domain.usecase.GetMovieDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideErrorMapper(): ErrorMapper = ErrorMapperImpl()

    @Singleton
    @Provides
    fun provideMovieRepository(
        api: MovieApi,
        database: MovieDatabase,
        photoStorage: PhotoStorageManager,
        errorMapper: ErrorMapper
    ): MovieRepository = MovieRepositoryImpl(api, database, photoStorage, errorMapper)

    @Singleton
    @Provides
    fun provideGetMoviesByCategoryUseCase(repository: MovieRepository): GetAllMoviesUseCase =
        GetAllMoviesUseCase(repository)

    @Singleton
    @Provides
    fun provideGetMovieDetailsUseCase(repository: MovieRepository): GetMovieDetailsUseCase =
        GetMovieDetailsUseCase(repository)

    @Singleton
    @Provides
    fun provideUpdateFavoriteStatusUseCase(repository: MovieRepository): UpdateFavoriteStatusUseCase =
        UpdateFavoriteStatusUseCase(repository)
}