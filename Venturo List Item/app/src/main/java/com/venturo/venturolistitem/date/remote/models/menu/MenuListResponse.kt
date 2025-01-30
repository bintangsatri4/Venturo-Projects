package com.venturo.venturolistitem.date.remote.models.menu

import com.venturo.venturolistitem.date.remote.models.Pagination

data class MenuListResponse(
    val data: List<com.venturo.venturolistitem.Menu> = emptyList(),
    val statusCode: Int = 500,
    val error: String? = null,
    val pagination: Pagination? = null
)
