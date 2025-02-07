package com.example.venturoimageoperation.data.remote.responses.menu

data class MenuListResponse(
    val data: List<Menu>? = null,
    val statusCode: Int = 500,
    val error: String? = null,
    val pagination: Pagination? = null
)

data class Menu(
    val id: Int,
    val name: String,
    val description: String
)

data class Pagination(
    val currentPage: Int,
    val totalPages: Int
)