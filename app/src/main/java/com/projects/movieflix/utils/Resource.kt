package com.projects.movieflix.utils

sealed class Resource<T>(
    val data: T? = null,
    val errorResId: Int? = null,
    val fallbackCode: Int? = null
) {
    class Success<T>(data: T, fallbackCode: Int? = null) : Resource<T>(data, null, fallbackCode)
    class Error<T>(errorResId: Int, data: T? = null) : Resource<T>(data, errorResId)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}