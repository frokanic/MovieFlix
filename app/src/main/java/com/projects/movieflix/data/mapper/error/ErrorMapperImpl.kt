package com.projects.movieflix.data.mapper.error

import com.projects.movieflix.R
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject

class ErrorMapperImpl @Inject constructor() : ErrorMapper {

    override fun mapError(exception: Throwable?): Int {

        return when (exception) {
            is IOException -> R.string.error_network

            is HttpException -> {
                when (exception.code()) {
                    // 404
                    HttpURLConnection.HTTP_NOT_FOUND -> R.string.error_not_found
                    // 401
                    HttpURLConnection.HTTP_UNAUTHORIZED -> R.string.error_not_authorized
                    // 503
                    HttpURLConnection.HTTP_UNAVAILABLE -> R.string.error_service_unavailable
                    // 500
                    HttpURLConnection.HTTP_INTERNAL_ERROR -> R.string.error_service_not_working

                    else -> R.string.error_unknown
                }
            }

            else -> {
                R.string.error_unknown
            }
        }
    }
}