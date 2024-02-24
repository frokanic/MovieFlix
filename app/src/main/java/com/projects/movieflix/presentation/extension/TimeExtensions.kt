package com.projects.movieflix.presentation.extension

/**
 * Converts an integer representing minutes into a formatted string of hours and minutes.
 * Example: 250 minutes becomes "4h 10min".
 */
fun Int.toHourMinuteFormat(): String {
    val hours = this / 60
    val minutes = this % 60
    return "${hours}h ${minutes}min"
}
