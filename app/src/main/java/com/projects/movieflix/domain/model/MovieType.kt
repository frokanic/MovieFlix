package com.projects.movieflix.domain.model

enum class MovieType(val categoryIdentifier: String, val displayTitle: String) {
    POPULAR("popular", "Popular"),
    UPCOMING("upcoming", "Upcoming"),
    NOW_PLAYING("now_playing", "Now Playing"),
    TOP_RATED("top_rated", "Top Rated");

    companion object {
        fun getDisplayTitleByCategoryIdentifier(categoryIdentifier: String): String? =
            entries.firstOrNull { it.categoryIdentifier == categoryIdentifier }?.displayTitle
    }
}