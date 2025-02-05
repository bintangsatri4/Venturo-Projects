package com.venturo.venturoreport.models

data class Order(
    val id: Int,
    val menu: List<Menu>,
    val status: Int,
    val quantity: Int,
    val date: String,
    val photo: String,
    val description: String,
    val kategori: String,
) {
    val totalBayar: Int
        get() = menu.sumOf { it.price }
}