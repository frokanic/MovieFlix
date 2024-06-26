package com.projects.movieflix.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

class TheMovieDBInterceptor: Interceptor {

    companion object {
        const val API_KEY = "api_key"
        const val LANGUAGE = "language"
        const val REGION = "region"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val httpURL = originalRequest.url

        val newHttpURLBuilder = httpURL.newBuilder()
        newHttpURLBuilder.addQueryParameter(API_KEY, NetworkConstants.MOVIE_DATABASE_API_KEY)
        newHttpURLBuilder.addQueryParameter(LANGUAGE, Locale.getDefault().language)
        newHttpURLBuilder.addQueryParameter(REGION, Locale.getDefault().country)
        val newHttpURL = newHttpURLBuilder.build()

        val newRequest = originalRequest.newBuilder().url(newHttpURL).build()
        return chain.proceed(newRequest)
    }
}