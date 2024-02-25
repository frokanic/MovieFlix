package com.projects.movieflix.utils.extensionfunctions

import com.google.gson.Gson

/**
 * Utility function for JSON serialization.
 * Provides extension to convert any object to JSON string using Gson.
 */
fun Any.toJson(): String = Gson().toJson(this)