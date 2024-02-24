package com.projects.movieflix.data.mapper.error

interface ErrorMapper {

    fun mapError(exception: Throwable?): Int

}