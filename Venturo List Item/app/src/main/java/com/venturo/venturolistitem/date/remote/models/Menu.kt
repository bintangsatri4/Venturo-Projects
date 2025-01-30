package com.venturo.venturolistitem.date.remote.models

import com.google.gson.annotations.SerializedName

data class Menu(
    @field:SerializedName("id_menu")
    val idMenu: Int?,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("category")
    val category: String?,
    @field:SerializedName("harga")
    val price: Int?,
    @field:SerializedName("deskripsi")
    val description: String?,
    @field:SerializedName("foto")
    val photo: String?,
    @field:SerializedName("status")
    val status: Int?
)