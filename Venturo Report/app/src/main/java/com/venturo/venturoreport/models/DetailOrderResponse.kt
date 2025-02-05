package com.venturo.venturoreport.models

data class DetailOrderResponse(
    val data: Order? = null,
    val statusCode: Int = 500,
    val error: String? = null
)
