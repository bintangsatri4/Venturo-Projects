package com.venturo.venturoreport.models

data class ListOrderResponse(
    val data: List<Order>? = null,
    val statusCode: Int = 500,
    val error: String? = null
)