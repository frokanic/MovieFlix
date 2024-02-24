package com.projects.movieflix.data.remote.response

data class ReviewsResponse(
    val id: Int,
    val page: Int,
    val results: List<ResultX>,
    val total_pages: Int,
    val total_results: Int
)

data class ResultX(
    val author: String,
    val author_details: AuthorDetails,
    val content: String,
    val created_at: String,
    val id: String,
    val updated_at: String,
    val url: String
)

data class AuthorDetails(
    val avatar_path: String,
    val name: String,
    val rating: Double,
    val username: String
)