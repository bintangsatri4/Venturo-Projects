package com.venturo.venturoreport.repository

import com.venturo.venturoreport.models.Menu
import com.venturo.venturoreport.models.Order

object DummyOrderList {
    val orders = listOf(
        Order(
            id = 1,
            menu = listOf(
                Menu(
                    id = 1,
                    name = "Nasi Goreng",
                    photo = "https://bing.com/th?id=OSK.4e90782ff84869ac38b58dc667487078",
                    kategori = "Makanan",
                    description = "Nasi goreng dengan bumbu spesial",
                    price = 20000
                ),
                Menu(
                    id = 2,
                    name = "Mie Goreng",
                    photo = "https://bing.com/th?id=OSK.fd2688729e6ece44519f962eb671566a",
                    price = 15000,
                    kategori = "Makanan",
                    description = "Mie goreng dengan bumbu spesial"
                ), Menu(
                    id = 1,
                    name = "Nasi Goreng",
                    photo = "https://bing.com/th?id=OSK.4e90782ff84869ac38b58dc667487078",
                    kategori = "Makanan",
                    description = "Nasi goreng dengan bumbu spesial",
                    price = 20000
                ),
                Menu(
                    id = 2,
                    name = "Mie Goreng",
                    photo = "https://bing.com/th?id=OSK.fd2688729e6ece44519f962eb671566a",
                    price = 15000,
                    kategori = "Makanan",
                    description = "Mie goreng dengan bumbu spesial"
                ),
                Menu(
                    id = 2,
                    name = "Mie Goreng Jawa",
                    photo = "https://bing.com/th?id=OSK.fd2688729e6ece44519f962eb671566a",
                    price = 15000,
                    kategori = "Makanan",
                    description = "Mie goreng dengan bumbu spesial"
                ),
                Menu(
                    id = 2,
                    name = "Nasi Goreng",
                    photo = "https://bing.com/th?id=OSK.4e90782ff84869ac38b58dc667487078",
                    price = 25000,
                    kategori = "Makanan",
                    description = "Mie goreng dengan bumbu spesial"
                ),
                Menu(
                    id = 22,
                    name = "Es Teh",
                    photo = "https://bing.com/th?id=OSK.fd2688729e6ece44519f962eb671566a",
                    price = 5000,
                    kategori = "Minuman",
                    description = "Mie goreng dengan bumbu spesial"
                ),
            ),
            quantity = 2,
            status = 0,
            date = "2023-10-26",
            photo = "https://example.com/order1.jpg",
            description = "Order 1 description",
            kategori = "Order 1 kategori"
        ),
        // Add other orders as needed
    )
}