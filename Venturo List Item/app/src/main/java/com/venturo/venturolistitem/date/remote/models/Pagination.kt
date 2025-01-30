package com.venturo.venturolistitem.date.remote.models

import com.google.gson.annotations.SerializedName

data class Pagination(
    @field:SerializedName("total_page")
    val totalPage: Int?,
    @field:SerializedName("limit")
    val limit: Int?,
    @field:SerializedName("is_last")
    val isLast: Boolean? = true
)