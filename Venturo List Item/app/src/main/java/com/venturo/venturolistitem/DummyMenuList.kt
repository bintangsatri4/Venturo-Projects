package com.venturo.venturolistitem

data class Menu(
    val idMenu: Int,
    val name: String,
    val category: String,
    val price: Int,
    val description: String,
    val photo: String,
    val status: Int
)

object DummyMenuList {
    val list = mutableListOf(
        Menu(
            idMenu = 2,
            name = "Chicken Slam",
            category = "food",
            price = 13000,
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            photo = "https://i.ibb.co/R9kJtny/1637916829.png",
            status = 1
        ),
        Menu(
            idMenu = 3,
            name = "Lemon Tea",
            category = "drink",
            price = 5000,
            description = "Daun Teh dengan irisan lemon",
            photo = "https://javacode.landa.id/img/menu/gambar_62660e379fdf4.png",
            status = 1
        ),
        Menu(
            idMenu = 3,
            name = "Lemon Tea",
            category = "drink",
            price = 5000,
            description = "Daun Teh dengan irisan lemon",
            photo = "https://javacode.landa.id/img/menu/gambar_62660e379fdf4.png",
            status = 1
        ),
        Menu(
            idMenu = 3,
            name = "Lemon Tea",
            category = "drink",
            price = 5000,
            description = "Daun Teh dengan irisan lemon",
            photo = "https://javacode.landa.id/img/menu/gambar_62660e379fdf4.png",
            status = 1
        )
        // Tambahkan data dummy lainnya sesuai kebutuhan
    )
}